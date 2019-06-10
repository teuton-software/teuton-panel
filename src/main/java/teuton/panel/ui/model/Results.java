package teuton.panel.ui.model;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Results {

	@SerializedName("title")
	private StringProperty title = new SimpleStringProperty();

	@SerializedName("case_id")
	private IntegerProperty caseId = new SimpleIntegerProperty();

	@SerializedName("start_time_")
	private StringProperty startTime = new SimpleStringProperty();

	@SerializedName("finish_time")
	private StringProperty finishTime = new SimpleStringProperty();

	@SerializedName("duration")
	private DoubleProperty duration = new SimpleDoubleProperty();

	@SerializedName("unique_fault")
	private IntegerProperty uniqueFault = new SimpleIntegerProperty();

	@SerializedName("max_weight")
	private DoubleProperty maxWeight = new SimpleDoubleProperty();

	@SerializedName("good_weight")
	private DoubleProperty goodWeight = new SimpleDoubleProperty();

	@SerializedName("fail_weight")
	private DoubleProperty failWeight = new SimpleDoubleProperty();

	@SerializedName("fail_counter")
	private IntegerProperty failCounter = new SimpleIntegerProperty();

	@SerializedName("grade")
	private IntegerProperty grade = new SimpleIntegerProperty();

	public final StringProperty titleProperty() {
		return this.title;
	}

	public final String getTitle() {
		return this.titleProperty().get();
	}

	public final void setTitle(final String title) {
		this.titleProperty().set(title);
	}

	public final IntegerProperty caseIdProperty() {
		return this.caseId;
	}

	public final int getCaseId() {
		return this.caseIdProperty().get();
	}

	public final void setCaseId(final int caseId) {
		this.caseIdProperty().set(caseId);
	}

	public final StringProperty startTimeProperty() {
		return this.startTime;
	}

	public final String getStartTime() {
		return this.startTimeProperty().get();
	}

	public final void setStartTime(final String startTime) {
		this.startTimeProperty().set(startTime);
	}

	public final StringProperty finishTimeProperty() {
		return this.finishTime;
	}

	public final String getFinishTime() {
		return this.finishTimeProperty().get();
	}

	public final void setFinishTime(final String finishTime) {
		this.finishTimeProperty().set(finishTime);
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

	public final IntegerProperty uniqueFaultProperty() {
		return this.uniqueFault;
	}

	public final int getUniqueFault() {
		return this.uniqueFaultProperty().get();
	}

	public final void setUniqueFault(final int uniqueFault) {
		this.uniqueFaultProperty().set(uniqueFault);
	}

	public final DoubleProperty maxWeightProperty() {
		return this.maxWeight;
	}

	public final double getMaxWeight() {
		return this.maxWeightProperty().get();
	}

	public final void setMaxWeight(final double maxWeight) {
		this.maxWeightProperty().set(maxWeight);
	}

	public final DoubleProperty goodWeightProperty() {
		return this.goodWeight;
	}

	public final double getGoodWeight() {
		return this.goodWeightProperty().get();
	}

	public final void setGoodWeight(final double goodWeight) {
		this.goodWeightProperty().set(goodWeight);
	}

	public final DoubleProperty failWeightProperty() {
		return this.failWeight;
	}

	public final double getFailWeight() {
		return this.failWeightProperty().get();
	}

	public final void setFailWeight(final double failWeight) {
		this.failWeightProperty().set(failWeight);
	}

	public final IntegerProperty failCounterProperty() {
		return this.failCounter;
	}

	public final int getFailCounter() {
		return this.failCounterProperty().get();
	}

	public final void setFailCounter(final int failCounter) {
		this.failCounterProperty().set(failCounter);
	}

	public final IntegerProperty gradeProperty() {
		return this.grade;
	}

	public final int getGrade() {
		return this.gradeProperty().get();
	}

	public final void setGrade(final int grade) {
		this.gradeProperty().set(grade);
	}

}
