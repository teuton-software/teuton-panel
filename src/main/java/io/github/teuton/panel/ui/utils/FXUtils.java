package io.github.teuton.panel.ui.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXUtils {
	
	public static void load(String fxml, Controller<?> controller, Node view) {
		try {
			FXMLLoader loader = new FXMLLoader(FXUtils.class.getResource(fxml));
			loader.setController(controller);
			loader.setRoot(view);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void load(String fxml, Node node) {
		try {
			FXMLLoader loader = new FXMLLoader(FXUtils.class.getResource(fxml));
			loader.setController(node);
			loader.setRoot(node);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void loadView(String fxml, Object controller) {
		try {
			FXMLLoader loader = new FXMLLoader(FXUtils.class.getResource(fxml));
			loader.setController(controller);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
