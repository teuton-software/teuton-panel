package io.github.teuton.panel.ui.model.resume;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Results {

	@SerializedName("start_time")
	private StringProperty startTime = new SimpleStringProperty();

	@SerializedName("finish_time")
	private StringProperty finishTime = new SimpleStringProperty();

	@SerializedName("duration")
	private DoubleProperty duration = new SimpleDoubleProperty();

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

}
