package teuton.panel.ui.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import teuton.panel.ui.mode.ModeController;
import teuton.panel.ui.mode.standalone.StandaloneController;
import teuton.panel.ui.utils.Controller;

public class MainController extends Controller<StackPane> {

	// controllers

	private ModeController modeController;
	private StandaloneController standaloneController;

	// model

	private StringProperty shown;
	private BooleanProperty loading;

	// view

	@FXML
	private BorderPane viewPane;

	@FXML
	private VBox loadingPane;

	// initialization

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// properties
		shown = new SimpleStringProperty(this, "shown");
		loading = new SimpleBooleanProperty(this, "loading");

		// create mode selector controller
		modeController = new ModeController();
		modeController.loadingProperty().bindBidirectional(loading);

		// create standalone mode controller
		standaloneController = new StandaloneController();
		standaloneController.loadingProperty().bindBidirectional(loading);

		// show loading pane "loading" property is true
		loadingPane.visibleProperty().bind(loading);

		// disable view pane when "loading" is enabled
		viewPane.disableProperty().bind(loading);

		// set a listener for changing view
		shown.addListener((o, ov, nv) -> onShownChanged(nv));

		shown.set("mode");
		loading.set(false);

	}

	private void onShownChanged(String nv) {
		switch (nv) {
		case "mode":
			viewPane.setCenter(modeController.getRoot());
			break;
		case "standalone":
			viewPane.setCenter(standaloneController.getRoot());
			break;
		default:
			viewPane.setCenter(null);
		}
	}

}
