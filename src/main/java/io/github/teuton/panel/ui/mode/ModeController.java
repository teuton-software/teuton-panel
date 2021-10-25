package io.github.teuton.panel.ui.mode;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import io.github.teuton.panel.ui.classroom.ClassroomController;
import io.github.teuton.panel.ui.model.Settings;
import io.github.teuton.panel.ui.settings.SettingsController;
import io.github.teuton.panel.ui.utils.Controller;
import io.github.teuton.panel.utils.DesktopUtils;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

	private ReadOnlyObjectWrapper<Settings> settings;

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

		settingsController = new SettingsController();

		settings = new ReadOnlyObjectWrapper<>();
		settings.bind(settingsController.settingsProperty());

		// TODO remove these lines
		contestButton.setDisable(true);
		standaloneButton.setDisable(true);
		
		Platform.runLater(() -> classroomButton.requestFocus()); 
		
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
		DesktopUtils.browse(ResourceBundle.getBundle("teuton-panel").getString("teuton.url"));
	}

	@FXML
	private void onTeutonPanelLinkAction(ActionEvent e) {
		DesktopUtils.browse(ResourceBundle.getBundle("teuton-panel").getString("teuton.panel.url"));
	}
	
	@FXML
	private void onClassroomModeButtonAction(ActionEvent event) {
		System.out.println("classroom");
		show(ClassroomController.class);
	}

	@FXML
	private void onContestModeButtonAction(ActionEvent event) {
		System.out.println("contest");
	}

	@FXML
	private void onStandaloneModeButtonAction(ActionEvent e) {
		System.out.println("stand-alone");
	}

	// ===================================
	// properties
	// ===================================
	
	public final ReadOnlyObjectProperty<Settings> settingsProperty() {
		return this.settings.getReadOnlyProperty();
	}

	public final Settings getSettings() {
		return this.settingsProperty().get();
	}

}
