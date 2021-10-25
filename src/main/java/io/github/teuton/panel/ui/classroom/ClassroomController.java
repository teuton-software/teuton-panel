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
	private ObjectProperty<Challenge> challenge;
	private StringProperty challengePath;
	private StringProperty configFilePath;

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
		challenge = new SimpleObjectProperty<>();
		challengePath = new SimpleStringProperty();
		configFilePath = new SimpleStringProperty();

		// bindings

		challengePath.bindBidirectional(challengeFolderText.textProperty());
		
		configFilePath.bindBidirectional(configFileText.textProperty());

		recentChallengesCombo.itemsProperty().bind(Config.getConfig().recentChallengesProperty());

		// listeners

		recentChallengesCombo.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> onRecentChallengeChanged(o, ov, nv));

		getRoot().sceneProperty().addListener((o, ov, nv) -> {
			if (nv != null) {
				recentChallengesCombo.getSelectionModel().clearSelection();
			}
		});

	}

	// ===================================
	// event listeners
	// ===================================

	private void onRecentChallengeChanged(ObservableValue<? extends Challenge> o, Challenge ov, Challenge nv) {
		if (nv != null) {
			challengePath.set(nv.getChallengeFolder());
			configFilePath.set(nv.getConfigFile());
		} else {
			challengePath.set("");
			configFilePath.set("");			
		}
	}

	@FXML
	private void onOpenAction(ActionEvent e) {
		
		String challengePath = this.challengePath.get();

		if (challengePath == null || !new File(challengePath).exists()) {
			Dialogs.error("Challenge folder doesn't exist!", "Folder '" + challengePath + "' can't be found.");
			return;
		}

		if (!new File(challengePath, "start.rb").exists()) {
			Dialogs.error("Selected folder is not a Teuton challenge", "Folder '" + challengePath + "' doesn't contain 'start.rb' file.");
			return;
		}

		// sets config file as config.yaml if empty
		String configFilePath = this.configFilePath.get();
		if (StringUtils.isBlank(configFilePath)) {
			configFilePath = challengePath + File.separator + "config.yaml";
		}

		// creates challenge
		Challenge challenge = new Challenge();
		challenge.setChallengeFolder(challengePath);
		challenge.setConfigFile(configFilePath);
		challenge.setTitle(new File(challengePath).getName());

		// set selected challenge (it's propagated to teacher controller)
		setChallenge(challenge);

		// stores opened challenge info
		Config.getConfig().addRecentChallenge(challenge);

		// change view to teacher mode
		show(TeacherController.class);

	}

	@FXML
	private void onChooseChallengeFolderAction(ActionEvent e) {
		DirectoryChooser dialog = new DirectoryChooser();
		dialog.setTitle("Choose challenge folder");
		dialog.setInitialDirectory(challengePath.get() == null ? new File(".") : new File(challengePath.get()));
		File file = dialog.showDialog(TeutonPanelApp.getPrimaryStage());
		if (file != null) {
			challengePath.set(file.getAbsolutePath());
		}
	}

	@FXML
	private void onChooseConfigFileAction(ActionEvent e) {
		
		File initialDirectory = challengePath.get() == null || !(new File(challengePath.get()).exists())? new File(".") : new File(challengePath.get());
		
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
