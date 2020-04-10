package io.github.teuton.panel.ui.components;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.utils.FXUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class ConfigEditorComponent extends BorderPane implements Initializable {

	// model

	private ObjectProperty<File> configFile = new SimpleObjectProperty<>();
	private StringProperty content = new SimpleStringProperty();

	// view

	@FXML
	private TextArea contentText;

	public ConfigEditorComponent() {
		FXUtils.load("/fxml/ConfigEditor.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contentText.textProperty().bind(content);
	}

	public final ObjectProperty<File> configFileProperty() {
		return this.configFile;
	}

	public final File getConfigFile() {
		return this.configFileProperty().get();
	}

	public final void setConfigFile(final File configFile) {
		this.configFileProperty().set(configFile);
	}

}
