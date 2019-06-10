package teuton.panel.ui.utils;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public abstract class Controller<R extends Parent> implements Initializable {
	
	@FXML
	protected R root;

	private ObjectProperty<Class<? extends Controller<?>>> shown = new SimpleObjectProperty<>();

	public Controller(String fxml) {
		load(fxml);
	}

	public Controller() {
		String name = getClass().getSimpleName().replaceAll("Controller$", "");
		String fxml = "/fxml/" + name + ".fxml";
		load(fxml);
	}
	
	public Controller(R root) {
		this.root = root;
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

	public final ObjectProperty<Class<? extends Controller<?>>> shownProperty() {
		return this.shown;
	}

	public final Class<? extends Controller<?>> getShown() {
		return this.shownProperty().get();
	}

	public final void setShown(final Class<? extends Controller<?>> shown) {
		this.shownProperty().set(shown);
	}

}
