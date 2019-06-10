package teuton.panel.ui.standalone;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import teuton.panel.ui.utils.Controller;

public class StandaloneController extends Controller<BorderPane> {

	// ===================================
	// model
	// ===================================


	// ===================================
	// view
	// ===================================

	@FXML
	private WebView challengeView, goalView;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab challengeTab, goalsTab;

	@FXML
	private JFXButton startButton, pauseButton, restartButton, openButton;

	// ===================================
	// constructor
	// ===================================

	public StandaloneController() {
		super("/fxml/Standalone.fxml");
	}

	// ===================================
	// initialization
	// ===================================

	@Override
	public void initialize(URL location, ResourceBundle resources) {


	}

	// ===================================
	// event listeners
	// ===================================

	@FXML
	private void onStartAction(ActionEvent e) {

	}

	@FXML
	private void onPauseAction(ActionEvent e) {

	}

	@FXML
	private void onRestartAction(ActionEvent e) {

	}

	@FXML
	private void onCloseAction(ActionEvent e) {

	}

}
