package io.github.teuton.panel.ui.tasks;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;

import io.github.teuton.Teuton;
import javafx.concurrent.Task;

public class PlayTask extends Task<File> {

	private StringBuffer buffer = new StringBuffer();
	private File challengeFolder;
	private String config;
	private File outputFolder;
	private List<String> selectedCases;

	public PlayTask(File challengeFolder, String config, File outputFolder, List<String> selectedCases) {
		super();
		this.challengeFolder = challengeFolder;
		this.config = config;
		this.outputFolder = outputFolder;
		this.selectedCases = selectedCases;
	}
		
	protected File call() throws Exception {
		
		File configFile = File.createTempFile("config_", ".yaml");
		FileUtils.writeStringToFile(configFile, config, StandardCharsets.UTF_8);
		
		Consumer<String> consumer = c -> appendMessage(c);
		
		appendMessage("---> PLAYING TEUTON...\n");
		
		Thread teutonThread = Teuton.play(challengeFolder, configFile, outputFolder, selectedCases, consumer, consumer);				
		teutonThread.join();

		appendMessage("---> TEUTON FINISHED!\n");
		
		return outputFolder;
	}
	
	private void appendMessage(String message) {
		buffer.append(message);
		updateMessage(buffer.toString());				
	}
	
	public void start() {
		new Thread(this).start();
	}
	
}
