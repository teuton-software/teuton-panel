package io.github.teuton.panel.ui.classroom;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import io.github.teuton.panel.ui.app.TeutonPanelApp;
import io.github.teuton.panel.ui.mode.ModeController;
import io.github.teuton.panel.ui.model.Settings;
import io.github.teuton.panel.ui.utils.Challenge;
import io.github.teuton.panel.ui.utils.Config;
import io.github.teuton.panel.ui.utils.Controller;
import io.github.teuton.panel.ui.utils.Dialogs;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class ClassroomController extends Controller<AnchorPane> {

	// ===================================
	// model
	// ===================================

	private ObjectProperty<Settings> settings;
	private ObjectProperty<Challenge> selectedChallenge;
	private StringProperty challengeFolderPath;
	private StringProperty configFilePath;
	private StringProperty resultsFilePath;

	// ===================================
	// view
	// ===================================

	@FXML
	private JFXButton openButton, backButton, chooseFolderButton;

	@FXML
	private ToggleButton teacherButton, studentButton;

	@FXML
	private JFXTextField challengeFolderText, configFileText, resultsFileText;

	@FXML
	private ToggleGroup selectedProfile;

	@FXML
	private HBox challengeFolderPane, configFilePane, resultsFilePane;

	@FXML
	private JFXComboBox<Challenge> recentChallengesCombo;

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

		// properties

		settings = new SimpleObjectProperty<>();
		selectedChallenge = new SimpleObjectProperty<>();
		challengeFolderPath = new SimpleStringProperty();
		configFilePath = new SimpleStringProperty();
		resultsFilePath = new SimpleStringProperty();

		// bindings

		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.getToggles().addAll(teacherButton, studentButton);

		challengeFolderPath.bindBidirectional(challengeFolderText.textProperty());
		configFilePath.bindBidirectional(configFileText.textProperty());
		resultsFilePath.bindBidirectional(resultsFileText.textProperty());

		openButton.disableProperty().bind(teacherButton.selectedProperty().and(challengeFolderPath.isNotEmpty()).or(studentButton.selectedProperty()).not());

		challengeFolderPane.disableProperty().bind(teacherButton.selectedProperty().not());
		configFilePane.disableProperty().bind(teacherButton.selectedProperty().not());
		recentChallengesCombo.disableProperty().bind(teacherButton.selectedProperty().not());

		resultsFilePane.disableProperty().bind(studentButton.selectedProperty().not());

		recentChallengesCombo.itemsProperty().bind(Config.getConfig().recentChallengesProperty());

		// listeners

		toggleGroup.selectedToggleProperty().addListener((o, ov, nv) -> onToggleButtonSelected(o, ov, nv));

		getRoot().sceneProperty().addListener((o, ov, nv) -> {
			if (nv != null) {
				selectedProfile.selectToggle(null);
				challengeFolderPath.set(null);
			}
		});

	}

	// ===================================
	// event listeners
	// ===================================

	private void onToggleButtonSelected(ObservableValue<? extends Toggle> o, Toggle ov, Toggle nv) {

		double x = 0;
		if (nv != null) {
			if (nv == teacherButton)
				x = -120;
			else
				x = 120;
		}

		TranslateTransition t = new TranslateTransition();
		t.setDuration(Duration.seconds(0.25));
		t.setInterpolator(Interpolator.EASE_BOTH);
		t.setNode(openButton);
		t.setToX(x);
		t.play();

	}

	@FXML
	private void onOpenAction(ActionEvent e) {
		if (teacherButton.isSelected()) {

			if (!new File(challengeFolderPath.get()).exists()) {
				Dialogs.error("Challenge folder doesn't exist!", "Folder '" + challengeFolderPath.get() + "' can't be found.");
				return;
			}

			if (!new File(challengeFolderPath.get(), "start.rb").exists()) {
				Dialogs.error("Selected folder is not a Teuton challenge", "Folder '" + challengeFolderPath.get() + "' doesn't contain 'start.rb' file.");
				return;
			}

			// creates challenge
			Challenge challenge = new Challenge();
			challenge.setChallengeFolder(challengeFolderPath.get());
			challenge.setConfigFile(configFilePath.get());
			challenge.setTitle(new File(challengeFolderPath.get()).getName());

			// set selected challenge (it's propagated to teacher controller)
			setSelectedChallenge(challenge);

			// stores opened challenge info
			Config.getConfig().addRecentChallenge(challenge);

			// change view to teacher mode
			setShown(TeacherController.class);

		} else {

			Dialogs.error("Not available", "Sorry, Teuton Panel is not yet available to students.");

		}
	}

	@FXML
	private void onChooseChallengeFolderAction(ActionEvent e) {
		DirectoryChooser dialog = new DirectoryChooser();
		dialog.setTitle("Choose challenge folder");
		dialog.setInitialDirectory(challengeFolderPath.get() == null ? new File(".") : new File(challengeFolderPath.get()));
		File file = dialog.showDialog(TeutonPanelApp.getPrimaryStage());
		if (file != null) {
			challengeFolderPath.set(file.getAbsolutePath());
		}
	}

	@FXML
	private void onChooseConfigFileAction(ActionEvent e) {
		FileChooser dialog = new FileChooser();
		dialog.setTitle("Choose config file");
		dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Config file", "*.yaml", "*.json"));
		dialog.setInitialDirectory(challengeFolderPath.get() == null ? new File(".") : new File(challengeFolderPath.get()));
		File file = dialog.showOpenDialog(TeutonPanelApp.getPrimaryStage());
		if (file != null) {
			configFilePath.set(file.getAbsolutePath());
		}
	}

	@FXML
	private void onChooseResultsFileAction(ActionEvent e) {
		FileChooser dialog = new FileChooser();
		dialog.setTitle("Choose results file");
		dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Config file", "*.json"));
		File file = dialog.showOpenDialog(TeutonPanelApp.getPrimaryStage());
		if (file != null) {
			resultsFilePath.set(file.getAbsolutePath());
		}
	}

	@FXML
	private void onBackAction(ActionEvent e) {
		setShown(ModeController.class);
	}

	// ===================================
	// properties
	// ===================================

	public final ObjectProperty<Settings> settingsProperty() {
		return this.settings;
	}

	public final Settings getSettings() {
		return this.settingsProperty().get();
	}

	public final void setSettings(final Settings settings) {
		this.settingsProperty().set(settings);
	}

	public final ObjectProperty<Challenge> selectedChallengeProperty() {
		return this.selectedChallenge;
	}

	public final Challenge getSelectedChallenge() {
		return this.selectedChallengeProperty().get();
	}

	public final void setSelectedChallenge(final Challenge selectedChallenge) {
		this.selectedChallengeProperty().set(selectedChallenge);
	}

}
