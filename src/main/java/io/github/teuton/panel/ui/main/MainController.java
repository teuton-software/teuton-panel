package io.github.teuton.panel.ui.main;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.classroom.TeacherController;
import io.github.teuton.panel.ui.classroom.ClassroomController;
import io.github.teuton.panel.ui.mode.ModeController;
import io.github.teuton.panel.ui.model.Settings;
import io.github.teuton.panel.ui.standalone.StandaloneController;
import io.github.teuton.panel.ui.utils.Challenge;
import io.github.teuton.panel.ui.utils.ParentController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	// model
	// ===================================
	
	private ObjectProperty<Challenge> challenge;
	private ObjectProperty<Settings> settings;
	private BooleanProperty loading;

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
		
		// create properties
		
		challenge = new SimpleObjectProperty<>();
		settings = new SimpleObjectProperty<>();
		loading = new SimpleBooleanProperty();

		// create mode selector controller
		modeController = new ModeController();

		// create classroom mode controller
		classroomController = new ClassroomController();
		classroomController.settingsProperty().bind(settings);
		classroomController.loadingProperty().bindBidirectional(loading);
		classroomController.challengeProperty().bindBidirectional(challenge);

		// create teacher controller
		teacherController = new TeacherController();
		teacherController.challengeProperty().bindBidirectional(challenge);
		teacherController.loadingProperty().bindBidirectional(loading);

		// create standalone mode controller
		standaloneController = new StandaloneController();

		// binding properties 
		
		settings.bind(modeController.settingsProperty());
		
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
