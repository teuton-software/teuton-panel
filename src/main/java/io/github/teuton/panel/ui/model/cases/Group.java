package io.github.teuton.panel.ui.model.cases;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {

	@SerializedName("title")
	private StringProperty title = new SimpleStringProperty();
	
	@SerializedName("targets")
	private ListProperty<Target> targets = new SimpleListProperty<>(FXCollections.observableArrayList());

	public final StringProperty titleProperty() {
		return this.title;
	}

	public final String getTitle() {
		return this.titleProperty().get();
	}

	public final void setTitle(final String title) {
		this.titleProperty().set(title);
	}

	public final ListProperty<Target> targetsProperty() {
		return this.targets;
	}

	public final ObservableList<Target> getTargets() {
		return this.targetsProperty().get();
	}

	public final void setTargets(final ObservableList<Target> targets) {
		this.targetsProperty().set(targets);
	}

}
