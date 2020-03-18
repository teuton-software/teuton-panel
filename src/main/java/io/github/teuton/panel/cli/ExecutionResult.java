package io.github.teuton.panel.cli;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.commons.exec.ExecuteWatchdog;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Command execution results.
 * 
 * @author Fran Vargas
 */
public class ExecutionResult {

	private ObjectProperty<ExecuteWatchdog> watchdog;
	private IntegerProperty exitValue;
	private ObjectProperty<InputStream> outputStream;
	private ObjectProperty<InputStream> errorStream;
	private StringProperty output;
	private StringProperty error;
	private StringProperty shellCommand;
	private StringProperty command;
	private ObjectProperty<LocalDateTime> executionTime;
	private ObjectProperty<Duration> duration;
	private BooleanProperty finished;

	public ExecutionResult() {
		this.watchdog = new SimpleObjectProperty<>(this, "watchdog");
		this.exitValue = new SimpleIntegerProperty(this, "exitValue", 0);
		this.outputStream = new SimpleObjectProperty<>(this, "outputStream");
		this.errorStream = new SimpleObjectProperty<>(this, "errorStream");
		this.output = new SimpleStringProperty(this, "output");
		this.error = new SimpleStringProperty(this, "error");
		this.shellCommand = new SimpleStringProperty(this, "shellCommand");
		this.command = new SimpleStringProperty(this, "command");
		this.executionTime = new SimpleObjectProperty<>(this, "executionTime");
		this.duration = new SimpleObjectProperty<>(this, "duration");
		this.finished = new SimpleBooleanProperty(this, "finished", false);
	}

	public final IntegerProperty exitValueProperty() {
		return this.exitValue;
	}

	public final int getExitValue() {
		return this.exitValueProperty().get();
	}

	public final void setExitValue(final int exitValue) {
		this.exitValueProperty().set(exitValue);
	}

	public final StringProperty shellCommandProperty() {
		return this.shellCommand;
	}

	public final String getShellCommand() {
		return this.shellCommandProperty().get();
	}

	public final void setShellCommand(final String shellCommand) {
		this.shellCommandProperty().set(shellCommand);
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

	public final ObjectProperty<LocalDateTime> executionTimeProperty() {
		return this.executionTime;
	}

	public final LocalDateTime getExecutionTime() {
		return this.executionTimeProperty().get();
	}

	public final void setExecutionTime(final LocalDateTime executionTime) {
		this.executionTimeProperty().set(executionTime);
	}

	public final ObjectProperty<Duration> durationProperty() {
		return this.duration;
	}

	public final Duration getDuration() {
		return this.durationProperty().get();
	}

	public final void setDuration(final Duration duration) {
		this.durationProperty().set(duration);
	}

	public final ObjectProperty<InputStream> outputStreamProperty() {
		return this.outputStream;
	}

	public final InputStream getOutputStream() {
		return this.outputStreamProperty().get();
	}

	public final void setOutputStream(final InputStream outputStream) {
		this.outputStreamProperty().set(outputStream);
	}

	public final ObjectProperty<InputStream> errorStreamProperty() {
		return this.errorStream;
	}

	public final InputStream getErrorStream() {
		return this.errorStreamProperty().get();
	}

	public final void setErrorStream(final InputStream errorStream) {
		this.errorStreamProperty().set(errorStream);
	}

	public final StringProperty outputProperty() {
		return this.output;
	}

	public final String getOutput() {
		return this.outputProperty().get();
	}

	public final void setOutput(final String output) {
		this.outputProperty().set(output);
	}

	public final StringProperty errorProperty() {
		return this.error;
	}

	public final String getError() {
		return this.errorProperty().get();
	}

	public final void setError(final String error) {
		this.errorProperty().set(error);
	}

	public final ObjectProperty<ExecuteWatchdog> watchdogProperty() {
		return this.watchdog;
	}

	public final ExecuteWatchdog getWatchdog() {
		return this.watchdogProperty().get();
	}

	public final void setWatchdog(final ExecuteWatchdog watchdog) {
		this.watchdogProperty().set(watchdog);
	}

	public final BooleanProperty finishedProperty() {
		return this.finished;
	}

	public final void setFinished(boolean finished) {
		this.finishedProperty().set(finished);
	}

	public final boolean isFinished() {
		return this.finishedProperty().get();
	}

	@Override
	public String toString() {
		return 
				"ExecutionResult {" + "\n" +
				"\t" + "command       : " + command.get() + "\n" +
				"\t" + "shellCommand  : " + shellCommand.get() + "\n" +
				"\t" + "executionTime : " + executionTime.get() + "\n" +
				"\t" + "duration      : " + duration.get().toMillis() + "ms\n" +
				"\t" + "exitValue     : " + exitValue.get() + "\n" +
				"\t" + "output:\n" + output.get() + "\n" +
				"\t" + "error:\n" + error.get() + "\n" +
				"}";
	}

}
