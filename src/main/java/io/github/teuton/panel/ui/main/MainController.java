package io.github.teuton.panel.ui.main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.classroom.ClassroomController;
import io.github.teuton.panel.ui.classroom.PreClassroomController;
import io.github.teuton.panel.ui.mode.ModeController;
import io.github.teuton.panel.ui.standalone.StandaloneController;
import io.github.teuton.panel.ui.utils.ParentController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MainController extends ParentController {

	// ===================================
	// controllers
	// ===================================

	private ModeController modeController;
	private StandaloneController standaloneController;
	private ClassroomController classroomController;
	private PreClassroomController preClassroomController;
	
	// ===================================
	// view
	// ===================================

	private BorderPane view;
	
	// ===================================
	// model
	// ===================================

	private ObjectProperty<File> selectedFile;

	// ===================================
	// initialization
	// ===================================

	public void initialize(URL location, ResourceBundle resources) {
		
		selectedFile = new SimpleObjectProperty<>();
		
		view = new BorderPane();		
		getRoot().getChildren().add(view);

		// create mode selector controller
		modeController = new ModeController();
		modeController.selectedFileProperty().bindBidirectional(selectedFile);

		// create standalone mode controller
		standaloneController = new StandaloneController();

		// create classroom mode controller
		classroomController = new ClassroomController();
		classroomController.selectedFileProperty().bind(selectedFile);
		
		// create pre-classroom controller
		preClassroomController = new PreClassroomController();
		preClassroomController.selectedFileProperty().bindBidirectional(selectedFile);
		preClassroomController.settingsProperty().bind(modeController.settingsProperty());
		
		// register controllers
		registerController(modeController);
		registerController(standaloneController);
		registerController(classroomController);
		registerController(preClassroomController);

		// init properties
		setShown(ModeController.class);

	}

	@Override
	protected void show(Parent node) {
		view.setCenter(node);
	}

}
