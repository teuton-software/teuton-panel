package io.github.teuton.panel.ui.utils;

import java.util.Optional;

import org.controlsfx.dialog.ExceptionDialog;

import io.github.teuton.panel.cli.CommandTask;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Dialogs {
	
	public static boolean confirm(String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(Application.getPrimaryStage());
		alert.setTitle(Application.getPrimaryStage().getTitle() + " :: " + "Confirm");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait(); 
		return result.isPresent() && result.get().equals(ButtonType.YES);
	}
	
	public static void info(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(Application.getPrimaryStage());
		alert.setTitle(Application.getPrimaryStage().getTitle() + " :: " + "Info");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void error(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(Application.getPrimaryStage());
		alert.setTitle(Application.getPrimaryStage().getTitle() + " :: " + "Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void exception(String header, String content, Throwable ex) {
		ExceptionDialog alert = new ExceptionDialog(ex);
		alert.initOwner(Application.getPrimaryStage());
		alert.setTitle(Application.getPrimaryStage().getTitle() + " :: " + "Exception");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static boolean runCommand(CommandTask task) {
		CommandDialog dialog = new CommandDialog(task);
		dialog.initOwner(Application.getPrimaryStage());
		dialog.setTitle(Application.getPrimaryStage().getTitle() + " :: " + "Command execution progress");		
		dialog.showAndWait();
		return task.getState().equals(State.SUCCEEDED);
	}

}
