package teuton.panel.ui.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Case {

	@SerializedName("config")
	private ObjectProperty<Config> config = new SimpleObjectProperty<>(new Config());

	@SerializedName("test")
	private ObjectProperty<Test> test = new SimpleObjectProperty<>(new Test());

	@SerializedName("results")
	private ObjectProperty<Results> results = new SimpleObjectProperty<>(new Results());

	public final ObjectProperty<Config> configProperty() {
		return this.config;
	}

	public final Config getConfig() {
		return this.configProperty().get();
	}

	public final void setConfig(final Config config) {
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
	
    public static Case load(File file) throws FileNotFoundException {
    	Reader json = new FileReader(file); 
		Gson gson = FxGson.create();
		return gson.fromJson(json, Case.class);
    }

}
