package io.github.teuton.panel.ui.main;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.classroom.TeacherController;
import io.github.teuton.panel.ui.classroom.ClassroomController;
import io.github.teuton.panel.ui.mode.ModeController;
import io.github.teuton.panel.ui.standalone.StandaloneController;
import io.github.teuton.panel.ui.utils.ParentController;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MainController extends ParentController {

	// ===================================
	// controllers
	// ===================================

	private ModeController modeController;
	private StandaloneController standaloneController;
	private TeacherController teacherController;
	private ClassroomController classroomController;
	
	// ===================================
	// view
	// ===================================

	private BorderPane view;
	
	// ===================================
	// initialization
	// ===================================

	public void initialize(URL location, ResourceBundle resources) {
		
		view = new BorderPane();		
		getRoot().getChildren().add(view);

		// create mode selector controller
		modeController = new ModeController();

		// create classroom mode controller
		classroomController = new ClassroomController();
		classroomController.settingsProperty().bind(modeController.settingsProperty());

		// create teacher controller
		teacherController = new TeacherController();
		teacherController.challengeProperty().bind(classroomController.selectedChallengeProperty());
		classroomController.loadingProperty().bind(teacherController.loadingProperty());
		
		// create standalone mode controller
		standaloneController = new StandaloneController();

		// register controllers
		registerController(modeController);
		registerController(classroomController);
		registerController(teacherController);
		registerController(standaloneController);

		// init properties
		show(ModeController.class);

	}

	@Override
	protected void show(Parent node) {
		view.setCenter(node);
	}

}
