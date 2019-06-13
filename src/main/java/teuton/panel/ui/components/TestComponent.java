package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import teuton.panel.ui.model.Target;
import teuton.panel.ui.model.Test;

public class TestComponent extends Accordion implements Initializable {

	// model

	private ListProperty<Target> targets = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Test> test = new SimpleObjectProperty<>();

	// view

	public TestComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Test.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		test.addListener((o, ov, nv) -> onTestChanged(o, ov, nv));
		
		targets.addListener((Change<? extends Target> c) -> onTargetsChanged(c));


	}
	
	private void onTargetsChanged(Change<? extends Target> changes) {
		getPanes().clear();
		while (changes.next()) {
			changes.getAddedSubList().stream().forEach(target -> {
				TargetComponent targetComponent = new TargetComponent();
				targetComponent.setTarget(target);
				getPanes().add(targetComponent);
			});
		}
	}

	private void onTestChanged(ObservableValue<? extends Test> o, Test ov, Test nv) {
		if (ov != null) {
			targets.unbind();
		}
		if (nv != null) {
			targets.bind(nv.targetsProperty());
		}
	}

	public final ObjectProperty<Test> testProperty() {
		return this.test;
	}

	public final Test getTest() {
		return this.testProperty().get();
	}

	public final void setTest(final Test test) {
		this.testProperty().set(test);
	}

}
