package teuton.panel.ui.model;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {

	@SerializedName("title")
	private StringProperty title = new SimpleStringProperty();
	
	@SerializedName("logs")
	private ListProperty<String> logs = new SimpleListProperty<>(FXCollections.observableArrayList());
	
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

	public final ListProperty<String> logsProperty() {
		return this.logs;
	}

	public final ObservableList<String> getLogs() {
		return this.logsProperty().get();
	}

	public final void setLogs(final ObservableList<String> logs) {
		this.logsProperty().set(logs);
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
