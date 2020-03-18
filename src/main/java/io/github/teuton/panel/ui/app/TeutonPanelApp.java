package io.github.teuton.panel.ui.app;

import io.github.teuton.panel.ui.main.MainController;
import io.github.teuton.panel.ui.utils.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TeutonPanelApp extends Application {
	
	public static final String TITLE = "Teuton Panel";

	private MainController mainController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		super.start(primaryStage);

		mainController = new MainController();

		primaryStage.setTitle(TITLE);
		primaryStage.setScene(new Scene(mainController.getRoot(), 640, 480));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/teuton-color-16x16.png").toExternalForm()));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/teuton-color-24x24.png").toExternalForm()));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/teuton-color-32x32.png").toExternalForm()));
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/teuton-color-48x48.png").toExternalForm()));
		primaryStage.show();

	}
	
	public static void run(String[] args) {
		launch(args);
	}

}
