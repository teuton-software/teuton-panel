package io.github.teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.model.Group;
import io.github.teuton.panel.ui.model.Test;
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

public class TargetComponent extends Accordion implements Initializable {

	// model

	private ListProperty<Group> groups = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Test> test = new SimpleObjectProperty<>();

	// view

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

		test.addListener((o, ov, nv) -> onTestChanged(o, ov, nv));
		
		groups.addListener((Change<? extends Group> c) -> onGroupsChanged(c));

	}
	
	private void onGroupsChanged(Change<? extends Group> changes) {
		getPanes().clear();
		while (changes.next()) {
			changes.getAddedSubList().stream().forEach(group -> {
				GroupComponent groupComponent = new GroupComponent();
				groupComponent.setGroup(group);
				getPanes().add(groupComponent);
			});
		}
	}

	private void onTestChanged(ObservableValue<? extends Test> o, Test ov, Test nv) {
		if (ov != null) {
			groups.unbind();
		}
		if (nv != null) {
			groups.bind(nv.groupsProperty());
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
