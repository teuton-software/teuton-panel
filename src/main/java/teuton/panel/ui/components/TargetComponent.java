package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import teuton.panel.ui.model.Target;

public class TargetComponent extends TitledPane implements Initializable {

	private ObjectProperty<Target> target = new SimpleObjectProperty<Target>();

	public TargetComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Target.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public final ObjectProperty<Target> targetProperty() {
		return this.target;
	}

	public final Target getTarget() {
		return this.targetProperty().get();
	}

	public final void setTarget(final Target target) {
		this.targetProperty().set(target);
	}

}
