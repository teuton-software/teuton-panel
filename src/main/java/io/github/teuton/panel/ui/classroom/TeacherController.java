package io.github.teuton.panel.ui.classroom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;

import io.github.teuton.Teuton;
import io.github.teuton.panel.ui.components.CasesComponent;
import io.github.teuton.panel.ui.components.ConfigEditorComponent;
import io.github.teuton.panel.ui.components.HallOfFameComponent;
import io.github.teuton.panel.ui.components.MarkdownComponent;
import io.github.teuton.panel.ui.components.OutputComponent;
import io.github.teuton.panel.ui.components.ResumeComponent;
import io.github.teuton.panel.ui.mode.ModeController;
import io.github.teuton.panel.ui.model.cases.Case;
import io.github.teuton.panel.ui.model.resume.Resume;
import io.github.teuton.panel.ui.utils.Challenge;
import io.github.teuton.panel.ui.utils.Config;
import io.github.teuton.panel.ui.utils.Controller;
import io.github.teuton.panel.ui.utils.Dialogs;
import io.github.teuton.panel.utils.DesktopUtils;
import io.github.teuton.panel.utils.JSONUtils;
import io.github.teuton.panel.utils.MarkdownUtils;
import io.github.teuton.panel.utils.StreamCharacterConsumer;
import javafx.beans.Observable;
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
import javafx.scene.layout.VBox;

public class TeacherController extends Controller<BorderPane> {

	// ===================================
	// model
	// ===================================
	
	private File varFolder = new File(Config.configDir, "var");

	private BooleanProperty running;
	private ListProperty<Case> cases;
	private ObjectProperty<Challenge> challenge;
	private StringProperty description;
	private ObjectProperty<Resume> resume;
	private StringProperty output;
	private StringProperty config;

	// ===================================
	// view
	// ===================================

	private MarkdownComponent descriptionComponent;
	private CasesComponent casesComponent;
	private HallOfFameComponent hallOfFameComponent;
	private OutputComponent outputComponent;
	private ResumeComponent resumeComponent;
	private ConfigEditorComponent configEditorComponent;

	@FXML
	private VBox runningPane;

	@FXML
	private JFXButton runButton, backButton;

	@FXML
	private Label testNameLabel;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab descriptionTab, casesTab, resumeTab, hallOfFameTab, outputTab, configTab;

	// ===================================
	// constructor
	// ===================================

	public TeacherController() {
		super("/fxml/Teacher.fxml");
	}

	// ===================================
	// initialization
	// ===================================

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// model

		running = new SimpleBooleanProperty(false);
		challenge = new SimpleObjectProperty<>();
		description = new SimpleStringProperty();
		resume = new SimpleObjectProperty<>();
		cases = new SimpleListProperty<>(FXCollections.observableArrayList());
		output = new SimpleStringProperty();
		config = new SimpleStringProperty();

		// components

		hallOfFameComponent = new HallOfFameComponent();
		hallOfFameTab.setContent(hallOfFameComponent);

		outputComponent = new OutputComponent();
		outputTab.setContent(outputComponent);

		casesComponent = new CasesComponent();
		casesTab.setContent(casesComponent);

		descriptionComponent = new MarkdownComponent();
		descriptionTab.setContent(descriptionComponent);

		resumeComponent = new ResumeComponent();
		resumeTab.setContent(resumeComponent);

		configEditorComponent = new ConfigEditorComponent();
		configTab.setContent(configEditorComponent);
		
		// bindings

		outputComponent.outputProperty().bind(output);
		casesComponent.casesProperty().bind(cases);
		descriptionComponent.markdownProperty().bind(description);
		resumeComponent.resumeProperty().bind(resume);

		testNameLabel.textProperty().bind(challenge.asString());

		runningPane.visibleProperty().bind(running);
		runningPane.managedProperty().bind(runningPane.visibleProperty());

		runButton.visibleProperty().bind(running.not());
		runButton.managedProperty().bind(runButton.visibleProperty());
		
		config.bind(configEditorComponent.contentProperty());

		// listeners

		challenge.addListener((o, ov, nv) -> onChallengeChanged(o, ov, nv));
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

	private void onChallengeChanged(Observable o, Challenge ov, Challenge nv) {
		if (nv != null) {
			configEditorComponent.setConfigFile(nv.getConfigFile() != null ? new File(nv.getConfigFile()) : null);
			loadResults();
			loadReadme();
		}
	}

	@FXML
	private void onRunAction(ActionEvent e) {
		play();
	}

	@FXML
	private void onRefreshAction(ActionEvent e) {
		loadResults();
	}

	@FXML
	private void onOpenFolderAction(ActionEvent e) {
		File challengeFolder = new File(getChallenge().getChallengeFolder());
		DesktopUtils.open(challengeFolder);
	}

	private void loadReadme() {
		Task<String> task = new Task<String>() {
			protected String call() throws Exception {
				String markdown = "";
				File challengeDirectory = new File(getChallenge().getChallengeFolder());
				File readmeFile = new File(challengeDirectory, "assets/README.md");
				if (readmeFile.exists()) {
					try {
						markdown = FileUtils.readFileToString(readmeFile, Charset.forName("UTF8"));
					} catch (IOException e) {
						throw new Exception("Description couldn't be loaded from " + readmeFile.getName(), e);
					}
				} else {
					markdown = Teuton.readme(challengeDirectory);
				}
				String html = "";
				try {
					html = MarkdownUtils.render(markdown);
				} catch (IOException e) {
					throw new Exception("Error rendering " + readmeFile.getName() + " description file", e);
				}
				return html;
			}
		};
		task.stateProperty().addListener((o, ov, nv) -> {
			descriptionComponent.setLoading(nv.equals(State.RUNNING));
		});
		task.setOnSucceeded(e -> {
			description.set(e.getSource().getValue().toString());
		});
		task.setOnFailed(e -> {
			Dialogs.exception("Error loading challenge description", e.getSource().getException().getMessage(),
					e.getSource().getException());
		});
		new Thread(task).start();
	}

	private void play() {
		final List<String> selectedCases = 
				cases.stream()
					.filter(c -> c.isSelected())
					.map(c -> c.getResults().get("case_id").toString())
					.collect(Collectors.toList());
		
		Task<Void> task = new Task<>() {
			protected Void call() throws Exception {
				File challengeFolder = new File(getChallenge().getChallengeFolder());
				
				File configFile = File.createTempFile("config_", ".yaml");
				FileUtils.writeStringToFile(configFile, config.get(), Charset.forName("UTF-8"));

				StringBuffer buffer = new StringBuffer();
				
				InputStream is = Teuton.play(challengeFolder, configFile, varFolder.getParentFile(), selectedCases);
				StreamCharacterConsumer consumer = new StreamCharacterConsumer(is, c -> {
					buffer.append(c);
					updateMessage(buffer.toString());
				});
				consumer.start();
				consumer.join();
				
				return null;
			}
		};
		task.stateProperty().addListener((o, ov, nv) -> {
			running.set(nv.equals(State.RUNNING));
		});
		task.setOnScheduled(e -> {
			output.bind(e.getSource().messageProperty());			
		});
		task.setOnSucceeded(e -> {
			loadResults();
			output.unbind();
		});
		task.setOnFailed(e -> {
			Dialogs.error("Error playing challenge", e.getSource().getException().getMessage());
			output.unbind();
		});
		new Thread(task).start();
		tabPane.getSelectionModel().select(outputTab);
	}

	private void loadResults() {
		File resultsFolder = new File(varFolder, getChallenge().getTitle());
		if (resultsFolder.exists()) {
			loadResume(resultsFolder);
			loadCases(resultsFolder);
		}
	}

	private void loadResume(File resultsFolder) {
		File resumeFile = new File(resultsFolder, "resume.json");
		try {
			resume.set(JSONUtils.loadFromJson(resumeFile, Resume.class));
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

	private void loadCases(File resultsFolder) {
		cases.clear();
		FileUtils.listFiles(resultsFolder, new WildcardFileFilter("case-*.json"), null).stream().forEach(f -> {
			try {
				cases.add(JSONUtils.loadFromJson(f, Case.class));
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		});
	}

	@FXML
	private void onBackAction(ActionEvent e) {
		setShown(ModeController.class);
	}

	// ===================================
	// properties
	// ===================================

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public final ObjectProperty<Challenge> challengeProperty() {
		return this.challenge;
	}

	public final Challenge getChallenge() {
		return this.challengeProperty().get();
	}

	public final void setChallenge(final Challenge challenge) {
		this.challengeProperty().set(challenge);
	}

}
