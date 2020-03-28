package io.github.teuton.panel.ui.model.resume;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Resume {

	@SerializedName("config")
	private MapProperty<String, Object> config = new SimpleMapProperty<String, Object>(
			FXCollections.observableHashMap());

	@SerializedName("cases")
	private ListProperty<Case> cases = new SimpleListProperty<>(FXCollections.observableArrayList());

	@SerializedName("results")
	private MapProperty<String, Object> results = new SimpleMapProperty<String, Object>(
			FXCollections.observableHashMap());

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

	public final MapProperty<String, String> hallOfFameProperty() {
		return this.hallOfFame;
	}

	public final ObservableMap<String, String> getHallOfFame() {
		return this.hallOfFameProperty().get();
	}

	public final void setHallOfFame(final ObservableMap<String, String> hallOfFame) {
		this.hallOfFameProperty().set(hallOfFame);
	}

	public final ListProperty<Case> casesProperty() {
		return this.cases;
	}

	public final ObservableList<Case> getCases() {
		return this.casesProperty().get();
	}

	public final void setCases(final ObservableList<Case> cases) {
		this.casesProperty().set(cases);
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

}
