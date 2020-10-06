package io.github.teuton.panel.ui.components;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import io.github.teuton.panel.ui.app.TeutonPanelApp;
import io.github.teuton.panel.ui.utils.Dialogs;
import io.github.teuton.panel.ui.utils.FXUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import net.lingala.zip4j.ZipFile;

public class ConfigEditorComponent extends BorderPane implements Initializable {

	// model

	private ObjectProperty<File> configFile = new SimpleObjectProperty<>();
	private StringProperty content = new SimpleStringProperty();

	// view

	@FXML
	private Label configFileLabel;

	@FXML
	private TextArea contentText;

	public ConfigEditorComponent() {
		FXUtils.load("/fxml/ConfigEditor.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contentText.textProperty().bindBidirectional(content);
		configFileLabel.textProperty().bind(configFile.asString());
		configFile.addListener((o, ov, nv) -> onConfigFileChanged(o, ov, nv));
	}

	private void onConfigFileChanged(ObservableValue<? extends File> o, File ov, File nv) {
		if (nv != null) {
			try {
				if (nv.exists())
					content.set(FileUtils.readFileToString(nv, Charset.forName("UTF-8")));
				else {
					content.set(IOUtils.resourceToString("/templates/config.yaml", Charset.forName("UTF-8")));
				}
			} catch (IOException e) {
				Dialogs.error("Error reading config file", e.getMessage());
			}
		}
	}

	@FXML
	private void onSaveAction(ActionEvent e) {

	}

	@FXML
	private void onReloadAction(ActionEvent e) {

	}
	
	@FXML
	private void onLoadConfigFileAction(ActionEvent e) {
		
	}

	@FXML
	private void onLoadFromMoodleZipAction(ActionEvent e) {

		try {

			// select moodle zip file
			FileChooser dialog = new FileChooser();
			dialog.setTitle("Choose Moodle submissions zip file");
			dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Moodle submissions file", "*.zip"));
			File file = dialog.showOpenDialog(TeutonPanelApp.getPrimaryStage());
			if (file == null)
				return;

			// destination
			File destination = Files.createTempDirectory("submissions_").toFile();

			// unzip file in temp folder
			ZipFile zipFile = new ZipFile(file);
			zipFile.extractAll(destination.getAbsolutePath());

			// explore destination
			StringBuffer buffer = new StringBuffer();
			List<File> submissions = Arrays.asList(destination.listFiles());
			for (File submission : submissions) {
				File[] files = submission.listFiles();
				if (files.length == 0)
					break; // skipping empty submission
				File configFile = files[0];
				String content = FileUtils.readFileToString(configFile, Charset.forName("UTF-8"));
				buffer.append(content.trim() + "\n");
			}

			// setting content
			setContent(getContent().trim() + (getContent().charAt(getContent().length() - 1) == '\n' ? "" : "\n") + buffer);

		} catch (IOException e1) {
			Dialogs.exception("Error", "Error loading config files fomr Moodle submissions zip file.", e1);
		}

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

	public final StringProperty contentProperty() {
		return this.content;
	}

	public final String getContent() {
		return this.contentProperty().get();
	}

	public final void setContent(final String content) {
		this.contentProperty().set(content);
	}

}
