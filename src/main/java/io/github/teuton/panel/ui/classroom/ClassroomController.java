package io.github.teuton.panel.ui.classroom;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

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
	private JFXTextField challengeFolderText, configFileText;

	@FXML
	private HBox challengeFolderPane, configFilePane;

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

		challengeFolderPath.bindBidirectional(challengeFolderText.textProperty());
		configFilePath.bindBidirectional(configFileText.textProperty());

		recentChallengesCombo.itemsProperty().bind(Config.getConfig().recentChallengesProperty());

		// listeners

		recentChallengesCombo.getSelectionModel().selectedItemProperty()
				.addListener((o, ov, nv) -> onRecentChallengeChanged(o, ov, nv));

		getRoot().sceneProperty().addListener((o, ov, nv) -> {
			if (nv != null) {
				challengeFolderPath.set(null);
			}
		});

	}

	// ===================================
	// event listeners
	// ===================================

	private void onRecentChallengeChanged(ObservableValue<? extends Challenge> o, Challenge ov, Challenge nv) {
		if (nv != null) {
			challengeFolderPath.set(nv.getChallengeFolder());
			configFilePath.set(nv.getConfigFile());
		}
	}

	@FXML
	private void onOpenAction(ActionEvent e) {

		if (challengeFolderPath.get() == null || !new File(challengeFolderPath.get()).exists()) {
			Dialogs.error("Challenge folder doesn't exist!", "Folder '" + challengeFolderPath.get() + "' can't be found.");
			return;
		}

		if (challengeFolderPath.get() == null || !new File(challengeFolderPath.get(), "start.rb").exists()) {
			Dialogs.error("Selected folder is not a Teuton challenge", "Folder '" + challengeFolderPath.get() + "' doesn't contain 'start.rb' file.");
			return;
		}

		// sets config file as config.yaml if empty
		String configFilePath = this.configFilePath.get();
		if (StringUtils.isBlank(configFilePath)) {
			configFilePath = challengeFolderPath.get() + "/config.yaml";
		}

		// creates challenge
		Challenge challenge = new Challenge();
		challenge.setChallengeFolder(challengeFolderPath.get());
		challenge.setConfigFile(configFilePath);
		challenge.setTitle(new File(challengeFolderPath.get()).getName());

		// set selected challenge (it's propagated to teacher controller)
		setSelectedChallenge(challenge);

		// stores opened challenge info
		Config.getConfig().addRecentChallenge(challenge);

		// change view to teacher mode
		show(TeacherController.class);

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
		
		File initialDirectory = challengeFolderPath.get() == null || !(new File(challengeFolderPath.get()).exists())? new File(".") : new File(challengeFolderPath.get());
		
		FileChooser dialog = new FileChooser();
		dialog.setTitle("Choose config file");
		dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Config file", "*.yaml", "*.json"));
		dialog.setInitialDirectory(initialDirectory);
		File file = dialog.showOpenDialog(TeutonPanelApp.getPrimaryStage());
		if (file != null) {
			configFilePath.set(file.getAbsolutePath());
		}
	}

	@FXML
	private void onBackAction(ActionEvent e) {
		show(ModeController.class);
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
