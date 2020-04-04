package io.github.teuton.panel.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.utils.FXUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class ConfigEditorComponent extends BorderPane implements Initializable {

	// model

	private StringProperty output = new SimpleStringProperty();

	// view

	@FXML
	private TextArea outputText;

	public ConfigEditorComponent() {
		FXUtils.load("/fxml/ConfigEditor.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		outputText.textProperty().bind(output);
	}

	public final StringProperty outputProperty() {
		return this.output;
	}

	public final String getOutput() {
		return this.outputProperty().get();
	}

	public final void setOutput(final String output) {
		this.outputProperty().set(output);
	}

}
