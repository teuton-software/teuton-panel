package io.github.teuton.panel.ui.model.cases;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Target {

	@SerializedName("target_id")
	private StringProperty targetId = new SimpleStringProperty();

	@SerializedName("check")
	private BooleanProperty check = new SimpleBooleanProperty();

	@SerializedName("score")
	private DoubleProperty score = new SimpleDoubleProperty();

	@SerializedName("weight")
	private DoubleProperty weight = new SimpleDoubleProperty();

	@SerializedName("description")
	private StringProperty description = new SimpleStringProperty();

	@SerializedName("command")
	private StringProperty command = new SimpleStringProperty();

	@SerializedName("conn_type")
	private StringProperty connType = new SimpleStringProperty();

	@SerializedName("duration")
	private DoubleProperty duration = new SimpleDoubleProperty();

	@SerializedName("alterations")
	private StringProperty alterations = new SimpleStringProperty();

	@SerializedName("expected")
	private StringProperty expected = new SimpleStringProperty();

	@SerializedName("result")
	private StringProperty result = new SimpleStringProperty();

	public final StringProperty targetIdProperty() {
		return this.targetId;
	}

	public final String getTargetId() {
		return this.targetIdProperty().get();
	}

	public final void setTargetId(final String targetId) {
		this.targetIdProperty().set(targetId);
	}

	public final BooleanProperty checkProperty() {
		return this.check;
	}

	public final boolean isCheck() {
		return this.checkProperty().get();
	}

	public final void setCheck(final boolean check) {
		this.checkProperty().set(check);
	}

	public final DoubleProperty scoreProperty() {
		return this.score;
	}

	public final double getScore() {
		return this.scoreProperty().get();
	}

	public final void setScore(final double score) {
		this.scoreProperty().set(score);
	}

	public final DoubleProperty weightProperty() {
		return this.weight;
	}

	public final double getWeight() {
		return this.weightProperty().get();
	}

	public final void setWeight(final double weight) {
		this.weightProperty().set(weight);
	}

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public final StringProperty commandProperty() {
		return this.command;
	}

	public final String getCommand() {
		return this.commandProperty().get();
	}

	public final void setCommand(final String command) {
		this.commandProperty().set(command);
	}

	public final StringProperty connTypeProperty() {
		return this.connType;
	}

	public final String getConnType() {
		return this.connTypeProperty().get();
	}

	public final void setConnType(final String connType) {
		this.connTypeProperty().set(connType);
	}

	public final DoubleProperty durationProperty() {
		return this.duration;
	}

	public final double getDuration() {
		return this.durationProperty().get();
	}

	public final void setDuration(final double duration) {
		this.durationProperty().set(duration);
	}

	public final StringProperty alterationsProperty() {
		return this.alterations;
	}

	public final String getAlterations() {
		return this.alterationsProperty().get();
	}

	public final void setAlterations(final String alterations) {
		this.alterationsProperty().set(alterations);
	}

	public final StringProperty expectedProperty() {
		return this.expected;
	}

	public final String getExpected() {
		return this.expectedProperty().get();
	}

	public final void setExpected(final String expected) {
		this.expectedProperty().set(expected);
	}

	public final StringProperty resultProperty() {
		return this.result;
	}

	public final String getResult() {
		return this.resultProperty().get();
	}

	public final void setResult(final String result) {
		this.resultProperty().set(result);
	}

}
