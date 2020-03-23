package io.github.teuton.panel.ui.classroom;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;

import io.github.teuton.Teuton;
import io.github.teuton.panel.ui.components.CaseComponent;
import io.github.teuton.panel.ui.components.HallOfFameComponent;
import io.github.teuton.panel.ui.components.MarkdownComponent;
import io.github.teuton.panel.ui.components.OutputComponent;
import io.github.teuton.panel.ui.components.WarningComponent;
import io.github.teuton.panel.ui.mode.ModeController;
import io.github.teuton.panel.ui.model.cases.Case;
import io.github.teuton.panel.ui.model.resume.Resume;
import io.github.teuton.panel.ui.utils.Controller;
import io.github.teuton.panel.ui.utils.Dialogs;
import io.github.teuton.panel.utils.JSONUtils;
import io.github.teuton.panel.utils.MarkdownUtils;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class ClassroomController extends Controller<BorderPane> {

	// ===================================
	// model
	// ===================================

	private BooleanProperty running;
	private ListProperty<Case> cases;
	private ObjectProperty<File> challenge;
	private StringProperty description;
	private ObjectProperty<File> results;
	private ObjectProperty<Resume> resume;
	private StringProperty output;

	// ===================================
	// view
	// ===================================

	private MarkdownComponent descriptionComponent;
	private CaseComponent caseComponent;
	private HallOfFameComponent hallOfFameComponent;
	private OutputComponent outputComponent;
	private WarningComponent warningComponent;

	@FXML
	private JFXSpinner runningSpinner;
	
	@FXML
	private JFXButton runButton, backButton;

	@FXML
	private JFXListView<Case> casesList;

	@FXML
	private Label testNameLabel;

	@FXML
	private BorderPane casePane;

	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab descriptionTab, casesTab, resumeTab, hallOfFameTab, outputTab;

	// ===================================
	// constructor
	// ===================================

	public ClassroomController() {
		super("/fxml/Classroom.fxml");
	}

	// ===================================
	// initialization
	// ===================================

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// initialize model
		
		running = new SimpleBooleanProperty(false);
		cases = new SimpleListProperty<>(FXCollections.observableArrayList());
		challenge = new SimpleObjectProperty<>();
		description = new SimpleStringProperty();
		results = new SimpleObjectProperty<>();
		resume = new SimpleObjectProperty<>();
		output = new SimpleStringProperty();
		
		// initialize view
		
		hallOfFameComponent = new HallOfFameComponent();
		hallOfFameTab.setContent(hallOfFameComponent);
		
		outputComponent = new OutputComponent();
		outputComponent.outputProperty().bind(output);
		outputTab.setContent(outputComponent);
		
		warningComponent = new WarningComponent();
		warningComponent.messageProperty().bind(Bindings.when(cases.emptyProperty()).then("Press 'Play' button to run the test").otherwise("Select a case from the list on the left"));

		caseComponent = new CaseComponent();
		
		descriptionComponent = new MarkdownComponent();
		descriptionComponent.markdownProperty().bind(description);			
		descriptionTab.setContent(descriptionComponent);

		casesList.setCellFactory(v -> new CaseListCell());
		casesList.itemsProperty().bind(cases);
		casesList.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> onCaseSelectionChanged(ov, nv));

		testNameLabel.textProperty().bind(challenge.asString());
		
		runningSpinner.visibleProperty().bind(running);
		runningSpinner.managedProperty().bind(runningSpinner.visibleProperty());

		runButton.visibleProperty().bind(running.not());
		runButton.managedProperty().bind(runButton.visibleProperty());
		
		// listeners
		
		challenge.addListener((o, ov, nv) -> onChallengaChanged(o, ov, nv));
		resume.addListener((o, ov, nv) -> onResumeChanged(o, ov, nv));
	}

	// ===================================
	// event listeners
	// ===================================

	private void onResumeChanged(ObservableValue<? extends Resume> o, Resume ov, Resume nv) {
		if (ov != null) {
			hallOfFameComponent.hallOfFameProperty().unbind();
		}
		if (nv != null) {
			hallOfFameComponent.hallOfFameProperty().bind(nv.hallOfFameProperty());			
		}
	}

	private  void onChallengaChanged(Observable o, File ov, File nv) {
		onCaseSelectionChanged(null, null);		
		if (nv != null) {
			results.set(new File(nv, "var/" + nv.getName()));
			loadResults();
			loadReadme();
		}
	}

	private void onCaseSelectionChanged(Case ov, Case nv) {
		if (nv == null) {
			casePane.setCenter(warningComponent);
			caseComponent.caseProperty().unbind();
		} else {
			casePane.setCenter(caseComponent);
			caseComponent.caseProperty().bind(casesList.getSelectionModel().selectedItemProperty());
		}
	}

	@FXML
	private void onRunAction(ActionEvent e) {
		play();
	}

	private void loadReadme() {
		String markdown = "";
		File challengeDirectory = challenge.get();
		File readmeFile = new File(challengeDirectory, "assets/README.md");
		if (readmeFile.exists()) {
			try {
				markdown = FileUtils.readFileToString(readmeFile, Charset.forName("UTF8"));
			} catch (IOException e) {
				Dialogs.exception("Description couldn't be loaded from README.md", e.getMessage(), e);
			}
		} else {
			markdown = Teuton.readme(challengeDirectory);
		}
		try {
			description.set(MarkdownUtils.render(markdown));
		} catch (IOException e) {
			Dialogs.exception("Error rendering " + readmeFile.getName() + " description file", e.getMessage(), e);
		}
	}

	private void play() {

		Task<String> task = new Task<String>() {
			protected String call() throws Exception {
				return Teuton.play(getChallenge());
			}
		};
		task.stateProperty().addListener((o, ov, nv) -> {
			running.set(nv.equals(State.RUNNING));
		});
		task.setOnSucceeded(e -> {
			loadResults();
			output.set(task.getValue());
			tabPane.getSelectionModel().select(outputTab);
		});
		task.setOnFailed(e -> {
			Dialogs.error("Error playing challenge", e.getSource().getException().getMessage());
		});
		new Thread(task).start();

	}

	private void loadResults() {
		loadResume();
		loadCases();
	}

	private void loadResume() {
		File resumeFile = new File(results.get(), "resume.json");
		try {
			resume.set(JSONUtils.loadFromJson(resumeFile, Resume.class));
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

	private void loadCases() {
		cases.clear();
		FileUtils.listFiles(results.get(), new WildcardFileFilter("case-*.json"), null).stream().forEach(f -> {
			try {
				cases.add(JSONUtils.loadFromJson(f, Case.class));
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		});
		casesList.getSelectionModel().selectFirst();
	}

	@FXML
	private void onBackAction(ActionEvent e) {
		setShown(ModeController.class);
	}

	// ===================================
	// properties
	// ===================================

	public final ObjectProperty<File> challengeProperty() {
		return this.challenge;
	}

	public final File getChallenge() {
		return this.challengeProperty().get();
	}

	public final void setChallenge(final File challenge) {
		this.challengeProperty().set(challenge);
	}

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

}
