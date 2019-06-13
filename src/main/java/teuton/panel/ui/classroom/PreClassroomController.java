package teuton.panel.ui.classroom;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import teuton.panel.ui.app.TeutonPanelApp;
import teuton.panel.ui.mode.ModeController;
import teuton.panel.ui.settings.Settings;
import teuton.panel.ui.utils.Controller;
import teuton.panel.ui.utils.Dialogs;

public class PreClassroomController extends Controller<AnchorPane> {

	// ===================================
	// model
	// ===================================

	private ObjectProperty<Settings> settings;
	private ObjectProperty<File> selectedFile;
	private StringProperty folder;

	// ===================================
	// view
	// ===================================

	@FXML
	private JFXButton startButton, backButton, chooseFolderButton;

	@FXML
	private ToggleButton teacherButton, studentButton;

	@FXML
	private JFXTextField folderText;

	@FXML
	private ToggleGroup selectedProfile;

	@FXML
	private HBox folderPane;

	// ===================================
	// constructor
	// ===================================

	public PreClassroomController() {
		super("/fxml/PreClassroom.fxml");
	}

	// ===================================
	// initialization
	// ===================================

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		settings = new SimpleObjectProperty<>();
		selectedFile = new SimpleObjectProperty<>();
		folder = new SimpleStringProperty();

		folder.bindBidirectional(folderText.textProperty());

		startButton.disableProperty().bind(
				teacherButton.selectedProperty().and(folder.isNotEmpty()).or(studentButton.selectedProperty()).not());

		folderPane.visibleProperty().bind(teacherButton.selectedProperty());

		getRoot().sceneProperty().addListener((o, ov, nv) -> {
			if (nv != null) {
				selectedProfile.selectToggle(null);
				folder.set(null);
			}
		});
	}

	// ===================================
	// event listeners
	// ===================================

	@FXML
	private void onStartAction(ActionEvent e) {
		if (teacherButton.isSelected()) {
			
			if (!settings.get().isTNode()) {
				Dialogs.error("You are not a T-Node!", "Sorry, you have to configure this node as a T-Node to continue.\nTake a look to settings panel.");
				return;
			}
			
			if (!new File(folder.get()).exists()) {
				Dialogs.error("Challenge folder doesn't exist!", "Folder '" + folder.get() + "' can't be found.");
				return;				
			}
			
			setSelectedFile(new File(folder.get()));
			
		} else {
			
			if (!settings.get().isSNode()) {
				Dialogs.error("You are not a S-Node!", "Sorry, you have to configure this node as a S-Node to continue.\nTake a look to settings panel.");
				return;
			}
			
		}
		setShown(ClassroomController.class);
	}

	@FXML
	private void onChooseFolderAction(ActionEvent e) {
		DirectoryChooser dialog = new DirectoryChooser();
		dialog.setTitle("Choose challenge folder");
		dialog.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents/teuton"));
		File file = dialog.showDialog(TeutonPanelApp.getPrimaryStage());
		if (file != null) {
			folder.set(file.getAbsolutePath());
		}
	}

	@FXML
	private void onBackAction(ActionEvent e) {
		setShown(ModeController.class);
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
