package teuton.panel.ui.classroom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import teuton.panel.cli.Command;
import teuton.panel.cli.CommandTask;
import teuton.panel.cli.ExecutionResult;
import teuton.panel.ui.components.CaseComponent;
import teuton.panel.ui.components.MarkdownComponent;
import teuton.panel.ui.components.WarningComponent;
import teuton.panel.ui.mode.ModeController;
import teuton.panel.ui.model.Case;
import teuton.panel.ui.settings.CommandFactory;
import teuton.panel.ui.utils.Controller;
import teuton.panel.ui.utils.Dialogs;
import teuton.panel.utils.MarkdownUtils;

public class ClassroomController extends Controller<BorderPane> {

	// ===================================
	// model
	// ===================================

	private ListProperty<Case> cases;
	private ObjectProperty<File> selectedFile;
	private StringProperty description;

	// ===================================
	// view
	// ===================================

	private MarkdownComponent descriptionComponent;
	private CaseComponent caseComponent;
	private WarningComponent warningComponent;

	@FXML
	private JFXButton runButton, pauseButton, backButton;

	@FXML
	private JFXListView<Case> casesList;

	@FXML
	private Label testNameLabel;

	@FXML
	private BorderPane casePane;
	
	@FXML
	private Tab descriptionTab;

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

		description = new SimpleStringProperty();

		cases = new SimpleListProperty<>(FXCollections.observableArrayList());

		selectedFile = new SimpleObjectProperty<>();

		// initialize view components
		
		warningComponent = new WarningComponent();
		warningComponent.messageProperty().bind(Bindings.when(cases.emptyProperty()).then("Press 'Play' button to run the test").otherwise("Select a case from the list on the left"));

		caseComponent = new CaseComponent();
		
		descriptionComponent = new MarkdownComponent();
		
		descriptionTab.setContent(descriptionComponent);

		casesList.setCellFactory(v -> new CaseListCell());
		casesList.itemsProperty().bind(cases);
		casesList.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> onCaseSelectionChanged(ov, nv));

		testNameLabel.textProperty().bind(selectedFile.asString());

		// listeners
		
		onCaseSelectionChanged(null, null);

		getRoot().sceneProperty().addListener((o, ov, nv) -> {
			loadResults(new File(selectedFile.get(), "var"));
			teutonReadme();
		});

	}

	// ===================================
	// event listeners
	// ===================================

	private void onCaseSelectionChanged(Case ov, Case nv) {
		if (nv == null) {
			casePane.setCenter(warningComponent);
			caseComponent.caseProperty().unbind();
			descriptionComponent.markdownProperty().unbind();
		} else {
			casePane.setCenter(caseComponent);
			caseComponent.caseProperty().bind(casesList.getSelectionModel().selectedItemProperty());
			descriptionComponent.markdownProperty().bind(description);			
		}
	}

	@FXML
	private void onRunAction(ActionEvent e) {

		teutonPlay();

	}

	private void teutonReadme() {
		File workingDirectory = selectedFile.get();

		File readmeFile = new File(workingDirectory, "assets/README.md");

		if (!readmeFile.exists()) {
			ExecutionResult result = CommandFactory.getCommand("teuton.readme").execute(true, workingDirectory);
			try {
				FileUtils.writeStringToFile(readmeFile, result.getOutput(), Charset.defaultCharset());
			} catch (IOException e) {
				Dialogs.exception("Description file README.md couldn't be generated", e.getMessage(), e);
			}
		}

		if (readmeFile.exists()) {
			try {
				String readmeMarkdown = FileUtils.readFileToString(readmeFile, Charset.forName("UTF8"));
				description.set(MarkdownUtils.render(readmeMarkdown));
			} catch (IOException e) {
				Dialogs.exception("Description couldn't be loaded from README.md", e.getMessage(), e);
			}
		}

	}

	private void teutonPlay() {
		File workingDirectory = selectedFile.get();

		Command cmd = CommandFactory.getCommand("teuton.play");

		CommandTask task = new CommandTask("Playing teuton background task", cmd);
		task.setWorkingDirectory(workingDirectory);
		task.getData().put("file", ".");
		task.setOnSucceeded(v -> {

			File outputDirectory = new File(workingDirectory, "var");
			loadResults(outputDirectory);

		});
		task.setOnFailed(v -> {
			Dialogs.exception(v.getSource().getException().getMessage(), "", v.getSource().getException());
		});

		Dialogs.runCommand(task);
	}

	private void loadResults(File varDirectory) {
		cases.clear();
		FileUtils.listFiles(varDirectory, new WildcardFileFilter("case-*.json"), null).stream().forEach(f -> {
			try {
				cases.add(Case.load(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		casesList.getSelectionModel().selectFirst();
	}

	@FXML
	private void onPauseAction(ActionEvent e) {
		System.out.println("pause");
	}

	@FXML
	private void onBackAction(ActionEvent e) {
		setShown(ModeController.class);
	}

	// ===================================
	// properties
	// ===================================

	public final ObjectProperty<File> selectedFileProperty() {
		return this.selectedFile;
	}

	public final File getSelectedFile() {
		return this.selectedFileProperty().get();
	}

	public final void setSelectedFile(final File selectedFile) {
		this.selectedFileProperty().set(selectedFile);
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
