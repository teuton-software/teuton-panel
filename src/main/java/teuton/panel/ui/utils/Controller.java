package teuton.panel.ui.utils;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public abstract class Controller<R extends Node> implements Initializable {
	
	@FXML
	protected R root;

	public Controller(String fxml) {
		load(fxml);
	}
	
	public Controller() {
		String name = getClass().getSimpleName().replaceAll("Controller$", "");
		String fxml = "/fxml/" + name + ".fxml";
		load(fxml);
	}
	
	private void load(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public final R getRoot() {
		return root;
	}
	
}
