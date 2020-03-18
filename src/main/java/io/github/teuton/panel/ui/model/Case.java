package io.github.teuton.panel.ui.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Case {

	@SerializedName("config")
	private MapProperty<String, Object> config = new SimpleMapProperty<String, Object>(
			FXCollections.observableHashMap());

	@SerializedName("test")
	private ObjectProperty<Test> test = new SimpleObjectProperty<>(new Test());

	@SerializedName("results")
	private ObjectProperty<Results> results = new SimpleObjectProperty<>(new Results());

	public static Case load(File file) throws FileNotFoundException {
		Reader json = new FileReader(file);
		Gson gson = FxGson.create();
		return gson.fromJson(json, Case.class);
	}

	public final MapProperty<String, Object> configProperty() {
		return this.config;
	}

	public final ObservableMap<String, Object> getConfig() {
		return this.configProperty().get();
	}

	public final void setConfig(final ObservableMap<String, Object> config) {
		this.configProperty().set(config);
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

	public final ObjectProperty<Results> resultsProperty() {
		return this.results;
	}

	public final Results getResults() {
		return this.resultsProperty().get();
	}

	public final void setResults(final Results results) {
		this.resultsProperty().set(results);
	}

}
