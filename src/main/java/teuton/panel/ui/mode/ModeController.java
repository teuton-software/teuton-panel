package teuton.panel.ui.mode;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import teuton.panel.cli.CommandTask;
import teuton.panel.ui.settings.CommandFactory;
import teuton.panel.ui.settings.SettingsController;
import teuton.panel.ui.utils.Controller;
import teuton.panel.ui.utils.Dialogs;

public class ModeController extends Controller<AnchorPane> {

	// controllers

	private SettingsController settingsController;

	// model

	private BooleanProperty loading;

	// view

	@FXML
	private JFXButton classroomButton, contestButton, standaloneButton, settingsButton;
	
	// initialization

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		settingsController = new SettingsController();

		loading = new SimpleBooleanProperty(this, "loading");

	}

	// event listeners

	@FXML
	private void onSettingsAction(ActionEvent e) {
		settingsController.showPopOver(settingsButton);		// se muestra el popup con las opciones
	}
	
	@FXML
	private void onTeutonLinkAction(ActionEvent e) {
		if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI("https://github.com/dvarrui/teuton"));
			} catch (Exception e1) {
				e1.printStackTrace();
				Dialogs.exception("Error opening browser", e1.getMessage(), e1);
			}
		}
	}
	
	@FXML
	private void onClassroomModeButtonAction(ActionEvent event) {
		System.out.println("classroom");
		
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
	private void onContestModeButtonAction(ActionEvent e) {
		System.out.println("contest");
	}

	@FXML
	private void onStandaloneModeButtonAction(ActionEvent e) {
		System.out.println("stand-alone");
	}

	// properties

	public final BooleanProperty loadingProperty() {
		return this.loading;
	}

	public final boolean isLoading() {
		return this.loadingProperty().get();
	}

	public final void setLoading(final boolean loading) {
		this.loadingProperty().set(loading);
	}

}
