package io.github.teuton.panel.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXUtils {
	
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

}
