package io.github.teuton.panel.ui.classroom;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;

import io.github.teuton.panel.ui.mode.ModeController;
import io.github.teuton.panel.ui.model.cases.Case;
import io.github.teuton.panel.ui.model.resume.Resume;
import io.github.teuton.panel.ui.panes.CasesPane;
import io.github.teuton.panel.ui.panes.ConfigEditorPane;
import io.github.teuton.panel.ui.panes.HallOfFamePane;
import io.github.teuton.panel.ui.panes.MarkdownPane;
import io.github.teuton.panel.ui.panes.OutputPane;
import io.github.teuton.panel.ui.panes.ResumePane;
import io.github.teuton.panel.ui.tasks.PlayTask;
import io.github.teuton.panel.ui.tasks.ReadmeTask;
import io.github.teuton.panel.ui.utils.Challenge;
import io.github.teuton.panel.ui.utils.Config;
import io.github.teuton.panel.ui.utils.Controller;
import io.github.teuton.panel.ui.utils.Dialogs;
import io.github.teuton.panel.utils.DesktopUtils;
import io.github.teuton.panel.utils.JSONUtils;
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

	private MarkdownPane descriptionComponent;
	private CasesPane casesComponent;
	private HallOfFamePane hallOfFameComponent;
	private OutputPane outputComponent;
	private ResumePane resumeComponent;
	private ConfigEditorPane configEditorComponent;

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

		hallOfFameComponent = new HallOfFamePane();
		hallOfFameTab.setContent(hallOfFameComponent);

		outputComponent = new OutputPane();
		outputTab.setContent(outputComponent);

		casesComponent = new CasesPane();
		casesTab.setContent(casesComponent);

		descriptionComponent = new MarkdownPane();
		descriptionTab.setContent(descriptionComponent);

		resumeComponent = new ResumePane();
		resumeTab.setContent(resumeComponent);

		configEditorComponent = new ConfigEditorPane();
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
	
	@FXML
	private void onBackAction(ActionEvent e) {
		if (Dialogs.confirm("Leaving the challenge", "Do you really want to do it?")) {
			setChallenge(null);
			show(ModeController.class);
		}
	}

	// ===================================
	// logic
	// ===================================
	
	private void loadReadme() {
		
		System.out.println("loading readme...........");
		
		File challengeDirectory = new File(getChallenge().getChallengeFolder());
		
		ReadmeTask task = new ReadmeTask(challengeDirectory);
		task.stateProperty().addListener((o, ov, nv) -> {
			descriptionComponent.setLoading(nv.equals(State.RUNNING));
		});
		task.setOnSucceeded(e -> {
			description.set(task.getValue());
		});
		task.setOnFailed(e -> {
			Dialogs.exception(
				"Error loading challenge description", 
				e.getSource().getException()
			);
		});
		task.start();
	}

	private void play() {
		List<String> selectedCases = 
				cases.stream()
					.filter(c -> c.isSelected())
					.map(c -> c.getResults().get("case_id").toString())
					.collect(Collectors.toList());
		
		PlayTask task = new PlayTask(
				new File(getChallenge().getChallengeFolder()),
				config.get(),
				varFolder.getParentFile(),
				selectedCases
			);
		task.stateProperty().addListener((o, ov, nv) -> {
			running.set(nv.equals(State.RUNNING));
		});
		task.setOnScheduled(e -> {
			output.bind(e.getSource().messageProperty());			
			tabPane.getSelectionModel().select(outputTab);
		});
		task.setOnSucceeded(e -> {
			loadResults();
			output.unbind();
		});
		task.setOnFailed(e -> {
			Dialogs.exception("Error playing challenge", e.getSource().getException());
			output.unbind();
		});
		task.start();
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
