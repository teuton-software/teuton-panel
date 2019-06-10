package teuton.panel.ui.classroom;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import teuton.panel.cli.Command;
import teuton.panel.cli.ExecutionResult;
import teuton.panel.ui.mode.ModeController;
import teuton.panel.ui.model.Case;
import teuton.panel.ui.settings.CommandFactory;
import teuton.panel.ui.utils.Controller;

public class ClassroomController extends Controller<BorderPane> {

	// ===================================
	// model
	// ===================================

	private ListProperty<Case> cases;
	private ObjectProperty<File> selectedFile;

	// ===================================
	// view
	// ===================================

	@FXML
	private JFXButton startButton, pauseButton, backButton;
	
	@FXML
	private JFXListView<Case> casesList;

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
		
		cases = new SimpleListProperty<>(FXCollections.observableArrayList());
		selectedFile = new SimpleObjectProperty<>();
		
		casesList.setCellFactory(v -> new CaseListCell());
		casesList.itemsProperty().bind(cases);
		
	}

	// ===================================
	// event listeners
	// ===================================

	@FXML
	private void onStartAction(ActionEvent e) {
		System.out.println("start");
		System.out.println(selectedFile.get().getAbsolutePath());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("file", ".");
		
		File workingDirectory = selectedFile.get();
		
		Command cmd = CommandFactory.getCommand("teuton.play");
		
		ExecutionResult result = cmd.execute(params, true, workingDirectory);
		System.out.println(result.getOutput());
		System.out.println(result.getError());
		
		File outputDirectory = new File(workingDirectory, "var");
		
		FileUtils
			.listFiles(outputDirectory, new WildcardFileFilter("case-*.json"), null)
			.stream()
			.forEach(f -> {
				try {
					cases.add(Case.load(f));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			});
		
	}

	@FXML
	private void onPauseAction(ActionEvent e) {
		System.out.println("pause");
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

}
