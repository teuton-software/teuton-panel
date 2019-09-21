package teuton.panel.cli;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.exec.util.MapUtils;
import org.apache.commons.lang.StringUtils;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import teuton.panel.utils.Translator;

public class Command {
	
	private static final Map<String, String> PROPERTIES_MAP = Translator.getMapFromProperties(System.getProperties());

	private ObjectProperty<Shell> shell;
	private StringProperty command;
	private ListProperty<String> params;

	public Command(Shell shell, String command, String... params) {
		this.shell = new SimpleObjectProperty<>(this, "shell", shell);
		this.command = new SimpleStringProperty(this, "command", command);
		this.params = new SimpleListProperty<>(this, "params", FXCollections.observableArrayList(params));
	}

	public final ObjectProperty<Shell> shellProperty() {
		return this.shell;
	}

	public final Shell getShell() {
		return this.shellProperty().get();
	}

	public final void setShell(final Shell shell) {
		this.shellProperty().set(shell);
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

	public final ListProperty<String> paramsProperty() {
		return this.params;
	}

	public final ObservableList<String> getParams() {
		return this.paramsProperty().get();
	}

	public final void setParams(final ObservableList<String> params) {
		this.paramsProperty().set(params);
	}
	
	@Override
	public String toString() {
		return (getCommand() + " " + StringUtils.join(getParams(), " ")).trim();
	}
	

	public ExecutionResult execute() {
		return execute(true);
	}
	
	public ExecutionResult execute(Map<String, String> data) {
		return execute(data, true);
	}

	public ExecutionResult execute(boolean waitFor) {
		return execute(Collections.emptyMap(), waitFor);
	}

	public ExecutionResult execute(Map<String, String> data, boolean waitFor) {
		return getShell().execute(this, MapUtils.merge(data, PROPERTIES_MAP), waitFor);
	}
	
	public ExecutionResult execute(Map<String, String> data, boolean waitFor, File workingDirectory) {
		return getShell().execute(this, MapUtils.merge(data, PROPERTIES_MAP), waitFor, workingDirectory);		
	}
	
	public ExecutionResult execute(boolean waitFor, File workingDirectory) {
		return execute(Collections.emptyMap(), waitFor, workingDirectory);		
	}

}
