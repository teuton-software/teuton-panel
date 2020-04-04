package io.github.teuton.panel.ui.model.cases;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Case {

	private BooleanProperty selected = new SimpleBooleanProperty();

	@SerializedName("config")
	private MapProperty<String, Object> config = new SimpleMapProperty<String, Object>(
			FXCollections.observableHashMap());

	@SerializedName("groups")
	private ListProperty<Group> groups = new SimpleListProperty<>(FXCollections.observableArrayList());

	@SerializedName("results")
	private MapProperty<String, Object> results = new SimpleMapProperty<String, Object>(
			FXCollections.observableHashMap());

	@SerializedName("logs")
	private ListProperty<String> logs = new SimpleListProperty<String>(FXCollections.observableArrayList());

	@SerializedName("hall_of_fame")
	private MapProperty<String, String> hallOfFame = new SimpleMapProperty<String, String>(
			FXCollections.observableHashMap());

	public final MapProperty<String, Object> configProperty() {
		return this.config;
	}

	public final ObservableMap<String, Object> getConfig() {
		return this.configProperty().get();
	}

	public final void setConfig(final ObservableMap<String, Object> config) {
		this.configProperty().set(config);
	}

	public final ListProperty<String> logsProperty() {
		return this.logs;
	}

	public final ObservableList<String> getLogs() {
		return this.logsProperty().get();
	}

	public final void setLogs(final ObservableList<String> logs) {
		this.logsProperty().set(logs);
	}

	public final MapProperty<String, String> hallOfFameProperty() {
		return this.hallOfFame;
	}

	public final ObservableMap<String, String> getHallOfFame() {
		return this.hallOfFameProperty().get();
	}

	public final void setHallOfFame(final ObservableMap<String, String> hallOfFame) {
		this.hallOfFameProperty().set(hallOfFame);
	}

	public final MapProperty<String, Object> resultsProperty() {
		return this.results;
	}

	public final ObservableMap<String, Object> getResults() {
		return this.resultsProperty().get();
	}

	public final void setResults(final ObservableMap<String, Object> results) {
		this.resultsProperty().set(results);
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

	public final BooleanProperty selectedProperty() {
		return this.selected;
	}

	public final boolean isSelected() {
		return this.selectedProperty().get();
	}

	public final void setSelected(final boolean selected) {
		this.selectedProperty().set(selected);
	}

}
