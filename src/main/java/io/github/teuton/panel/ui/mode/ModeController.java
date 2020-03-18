package io.github.teuton.panel.ui.mode;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import io.github.teuton.panel.cli.CommandTask;
import io.github.teuton.panel.ui.classroom.PreClassroomController;
import io.github.teuton.panel.ui.settings.CommandFactory;
import io.github.teuton.panel.ui.settings.Settings;
import io.github.teuton.panel.ui.settings.SettingsController;
import io.github.teuton.panel.ui.utils.Controller;
import io.github.teuton.panel.ui.utils.Dialogs;
import io.github.teuton.panel.utils.DesktopUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ModeController extends Controller<AnchorPane> {

	// ===================================
	// controllers
	// ===================================

	private SettingsController settingsController;

	// ===================================
	// model
	// ===================================

	private ObjectProperty<Settings> settings;
	private ObjectProperty<File> selectedFile;

	// ===================================
	// view
	// ===================================

	@FXML
	private JFXButton classroomButton, contestButton, standaloneButton, settingsButton;

	// ===================================
	// initialization
	// ===================================

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		selectedFile = new SimpleObjectProperty<>();

		settingsController = new SettingsController();

		settings = new SimpleObjectProperty<Settings>();
		settings.bind(settingsController.settingsProperty());

	}

	// ===================================
	// event listeners
	// ===================================

	@FXML
	private void onSettingsAction(ActionEvent e) {
		settingsController.showPopOver(settingsButton); // se muestra el popup con las opciones
	}

	@FXML
	private void onTeutonLinkAction(ActionEvent e) {
		DesktopUtils.browse(ResourceBundle.getBundle("teuton-panel").getString("teuton.panel.project.link"));
	}

	@FXML
	private void onClassroomModeButtonAction(ActionEvent event) {
		System.out.println("classroom");
		setShown(PreClassroomController.class);
	}

	@FXML
	private void onContestModeButtonAction(ActionEvent event) {
		System.out.println("contest");

		CommandTask task = new CommandTask("Do nothing", CommandFactory.getCommand("do.nothing"));
		task.setOnSucceeded(e -> {
			Dialogs.info("Do Nothing", "Sucess");
		});
		task.setOnFailed(e -> {
			Dialogs.exception("Do Nothing", "Failed", e.getSource().getException());
		});
		task.setOnCancelled(e -> {
			Dialogs.error("Do Nothing", "Cancelled");
		});
		Dialogs.runCommand(task);

	}

	@FXML
	private void onStandaloneModeButtonAction(ActionEvent e) {
		System.out.println("stand-alone");
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

	public final ObjectProperty<Settings> settingsProperty() {
		return this.settings;
	}

	public final Settings getSettings() {
		return this.settingsProperty().get();
	}

	public final void setSettings(final Settings settings) {
		this.settingsProperty().set(settings);
	}

}
