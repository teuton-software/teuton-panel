package io.github.teuton.panel.ui.model;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {

	@SerializedName("logs")
	private ListProperty<String> logs = new SimpleListProperty<>(FXCollections.observableArrayList());

	@SerializedName("groups")
	private ListProperty<Group> groups = new SimpleListProperty<>(FXCollections.observableArrayList());

	public final ListProperty<String> logsProperty() {
		return this.logs;
	}

	public final ObservableList<String> getLogs() {
		return this.logsProperty().get();
	}

	public final void setLogs(final ObservableList<String> logs) {
		this.logsProperty().set(logs);
	}

	public final ListProperty<Group> groupsProperty() {
		return this.groups;
	}

	public final ObservableList<Group> getGroups() {
		return this.groupsProperty().get();
	}

	public final void setGroups(final ObservableList<Group> groups) {
		this.groupsProperty().set(groups);
	}

}
