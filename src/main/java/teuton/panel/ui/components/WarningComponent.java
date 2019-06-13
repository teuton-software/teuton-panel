package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WarningComponent extends VBox implements Initializable {

	// model

	private StringProperty message = new SimpleStringProperty();

	// view

	@FXML
	private Label messageLabel;

	public WarningComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Warning.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		messageLabel.textProperty().bind(message);

	}

	public final StringProperty messageProperty() {
		return this.message;
	}

	public final String getMessage() {
		return this.messageProperty().get();
	}

	public final void setMessage(final String message) {
		this.messageProperty().set(message);
	}

}
