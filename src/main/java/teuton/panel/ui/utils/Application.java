package teuton.panel.ui.utils;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {

	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Application.primaryStage = primaryStage;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

}
