package io.github.teuton.panel.cli;

import java.io.File;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Provide an execution environment for commands.
 * 
 * @author Fran Vargas
 *
 */
public class Shell {

	private StringProperty name;
	private StringProperty executable;
	private ListProperty<String> arguments;

	public Shell(String name, String executable, String ... arguments) {
		this.name = new SimpleStringProperty(this, "name", name);
		this.executable = new SimpleStringProperty(this, "executable", executable);
		this.arguments = new SimpleListProperty<>(this, "arguments", FXCollections.observableArrayList(arguments));
	}

	public Shell() {
		this(null, null);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public StringProperty executableProperty() {
		return this.executable;
	}

	public String getExecutable() {
		return this.executableProperty().get();
	}

	public void setExecutable(final String command) {
		this.executableProperty().set(command);
	}

	public final ListProperty<String> argumentsProperty() {
		return this.arguments;
	}

	public final ObservableList<String> getArguments() {
		return this.argumentsProperty().get();
	}

	public final void setArguments(final ObservableList<String> arguments) {
		this.argumentsProperty().set(arguments);
	}

	public ExecutionResult execute(Command command, Map<String, String> data, boolean waitFor) {
		return execute(command, getArguments(), data, waitFor);
	}
	
	public ExecutionResult execute(Command command, Map<String, String> data, boolean waitFor, File workingDirectory) {
		return execute(command, getArguments(), data, waitFor, workingDirectory);
	}

	public ExecutionResult execute(Command command) {
		return execute(command, Collections.emptyMap());
	}

	public ExecutionResult execute(Command command, Map<String, String> data) {
		return execute(command, data, true);
	}
	
	private ExecutionResult execute(Command command, List<String> arguments, Map<String, String> data, boolean waitFor) {
		return execute(command, arguments, data, waitFor, new File("."));
	}

	private ExecutionResult execute(Command command, List<String> arguments, Map<String, String> data, boolean waitFor, File workingDirectory) {
		ExecutionResult result = new ExecutionResult();

		try {

			final StopWatch chrono = new StopWatch();

			result.setExecutionTime(LocalDateTime.now());

			String commandString = Matcher.quoteReplacement(command.getCommand());
			String paramsString = StringUtils.join(command.getParams(), " ");
			
			CommandLine cmdLine = new CommandLine(getExecutable());
			for (String argument : arguments) {
				argument = argument.replaceAll(Pattern.quote("${command}"), commandString); 
				argument = argument.replaceAll(Pattern.quote("${script}"), commandString); 
				argument = argument.replaceAll(Pattern.quote("${params}"), paramsString);
				cmdLine.addArgument(argument, false);
			}
			cmdLine.setSubstitutionMap(data);
			
			result.setCommand((commandString + " " + paramsString).trim());
			result.setShellCommand(cmdLine.getExecutable() + " " + StringUtils.join(cmdLine.getArguments(), " "));

			DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler() {
				private void update(int exitValue) {
					chrono.stop();
					result.setExitValue(exitValue);
					result.setDuration(Duration.ofNanos(chrono.getNanoTime()));
					result.setFinished(true);
				}

				public void onProcessComplete(int exitValue) {
					super.onProcessComplete(exitValue);
					update(exitValue);
				}

				public void onProcessFailed(ExecuteException e) {
					super.onProcessFailed(e);
					update(getExitValue());
				}
			};

			PipedOutputStream output = new PipedOutputStream();
			PipedOutputStream error = new PipedOutputStream();

			PumpStreamHandler streamHandler = new PumpStreamHandler(output, error);
			
			ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);

			result.setOutputStream(new PipedInputStream(output));
			result.setErrorStream(new PipedInputStream(error));
			result.setWatchdog(watchdog);

			Executor executor = new DefaultExecutor();
			executor.setStreamHandler(streamHandler);
			executor.setWatchdog(watchdog);
			executor.setWorkingDirectory(workingDirectory);
			executor.execute(cmdLine, handler);
			
			if (waitFor) {
				result.setOutput(IOUtils.toString(result.getOutputStream(), Charset.defaultCharset()).trim());
				result.setError(IOUtils.toString(result.getErrorStream(), Charset.defaultCharset()).trim());
				handler.waitFor();
			}

		} catch (Exception e) {

			result.setError(e.getMessage());
			result.setExitValue(-1);
			e.printStackTrace();

		}
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shell other = (Shell) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getName() + " (" + getExecutable() + ")";
	}

	public static void main(String[] args) throws InterruptedException {
		StopWatch watch = new StopWatch();
		watch.start();
		Thread.sleep(1234L);
		watch.stop();
		System.out.println(watch.getTime());
	}
	
}
