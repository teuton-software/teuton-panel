package teuton.panel.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import teuton.panel.utils.MessageConsumer;

/**
 * Background command execution
 * 
 * @author fvarrui
 */
public class CommandTask extends Task<ExecutionResult> {

	private MessageConsumer consumer;

	private static final Pattern INFO_REGEX = Pattern.compile("^\\[([0-9]*)/([0-9]*)\\.INFO\\] *(.*)$");
	private static final Predicate<String> INFO_PREDICATE = INFO_REGEX.asPredicate();

	private ObjectProperty<Command> command = new SimpleObjectProperty<>(this, "command");

	public CommandTask(String title, Command command) {
		super();
		updateTitle(title);
		setCommand(command);
	}

	@Override
	protected ExecutionResult call() throws Exception {

		ExecutionResult result = getCommand().execute(false);

		BufferedReader outputReader = new BufferedReader(new InputStreamReader(result.getOutputStream()));
		BufferedReader errorReader = new BufferedReader(new InputStreamReader(result.getErrorStream()));

		StringBuffer output = new StringBuffer();
		StringBuffer error = new StringBuffer();

		updateProgress(0, 100);

		Platform.runLater(() -> {
			if (consumer != null) consumer.start();
		});

		while ((!isCancelled() && !result.isFinished()) || outputReader.ready() || errorReader.ready()) {

			if (outputReader.ready()) {

				String line = outputReader.readLine();
				output.append(line + "\n");
				if (consumer != null) consumer.putMessage(line);

				// update progress bar
				if (matches(line)) {
					Info info = new Info(line);
					updateProgress(info.getWorkDone(), info.getMax());
					updateMessage(info.getMessage());
				}

			}

			if (errorReader.ready()) {
				String line = errorReader.readLine();
				error.append(line + "\n");
				if (consumer != null) consumer.putMessage(line);
			}

		}

		if (isCancelled()) {
			result.getWatchdog().destroyProcess();
			result.setExitValue(-1);
			result.setError("Execution cancelled");
		} else {
			result.setOutput(output.toString());
			result.setError(error.toString());
		}

		if (result.getExitValue() != 0) {
			throw new Exception(result.getError());
		}

		return result;
	}

	private class Info {
		private long workDone = 0;
		private long max = 0;
		private String message = "";

		public Info(String line) {
			Matcher m = INFO_REGEX.matcher(line);
			if (m.matches()) {
				setWorkDone(Long.parseLong(m.group(1)));
				setMax(Long.parseLong(m.group(2)));
				setMessage(m.group(3));
			}
		}

		public long getWorkDone() {
			return workDone;
		}

		public void setWorkDone(long workDone) {
			this.workDone = workDone;
		}

		public long getMax() {
			return max;
		}

		public void setMax(long max) {
			this.max = max;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

	public final ObjectProperty<Command> commandProperty() {
		return this.command;
	}

	public final Command getCommand() {
		return this.commandProperty().get();
	}

	public final void setCommand(final Command command) {
		this.commandProperty().set(command);
	}

	public boolean matches(String line) {
		return INFO_PREDICATE.test(line);
	}

	public void setConsumer(MessageConsumer consumer) {
		this.consumer = consumer;
	}

}
