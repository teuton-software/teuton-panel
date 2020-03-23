package io.github.teuton.panel.ui.model.resume;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Case {

	@SerializedName("skip")
	private BooleanProperty skip = new SimpleBooleanProperty();

	@SerializedName("id")
	private StringProperty id = new SimpleStringProperty();

	@SerializedName("grade")
	private DoubleProperty grade = new SimpleDoubleProperty();

	@SerializedName("members")
	private StringProperty members = new SimpleStringProperty();

	@SerializedName("conn_status")
	private MapProperty<String, Object> connStatus = new SimpleMapProperty<String, Object>(FXCollections.observableHashMap());

	@SerializedName("moodle_id")
	private StringProperty moodleId = new SimpleStringProperty();

	@SerializedName("moodle_feedback")
	private StringProperty moodleFeedback = new SimpleStringProperty();

	public final BooleanProperty skipProperty() {
		return this.skip;
	}

	public final boolean isSkip() {
		return this.skipProperty().get();
	}

	public final void setSkip(final boolean skip) {
		this.skipProperty().set(skip);
	}

	public final StringProperty idProperty() {
		return this.id;
	}

	public final String getId() {
		return this.idProperty().get();
	}

	public final void setId(final String id) {
		this.idProperty().set(id);
	}

	public final DoubleProperty gradeProperty() {
		return this.grade;
	}

	public final double getGrade() {
		return this.gradeProperty().get();
	}

	public final void setGrade(final double grade) {
		this.gradeProperty().set(grade);
	}

	public final StringProperty membersProperty() {
		return this.members;
	}

	public final String getMembers() {
		return this.membersProperty().get();
	}

	public final void setMembers(final String members) {
		this.membersProperty().set(members);
	}

	public final MapProperty<String, Object> connStatusProperty() {
		return this.connStatus;
	}

	public final ObservableMap<String, Object> getConnStatus() {
		return this.connStatusProperty().get();
	}

	public final void setConnStatus(final ObservableMap<String, Object> connStatus) {
		this.connStatusProperty().set(connStatus);
	}

	public final StringProperty moodleIdProperty() {
		return this.moodleId;
	}

	public final String getMoodleId() {
		return this.moodleIdProperty().get();
	}

	public final void setMoodleId(final String moodleId) {
		this.moodleIdProperty().set(moodleId);
	}

	public final StringProperty moodleFeedbackProperty() {
		return this.moodleFeedback;
	}

	public final String getMoodleFeedback() {
		return this.moodleFeedbackProperty().get();
	}

	public final void setMoodleFeedback(final String moodleFeedback) {
		this.moodleFeedbackProperty().set(moodleFeedback);
	}

}
