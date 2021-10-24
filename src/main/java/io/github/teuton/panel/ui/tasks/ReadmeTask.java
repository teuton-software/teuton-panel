package io.github.teuton.panel.ui.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import io.github.teuton.Teuton;
import io.github.teuton.panel.utils.MarkdownUtils;
import javafx.concurrent.Task;

public class ReadmeTask extends Task<String> {
	
	private File challengeDirectory;
	
	public ReadmeTask(File challengeDirectory) {
		super();
		this.challengeDirectory = challengeDirectory;
	}

	@Override
	protected String call() throws Exception {
		String markdown = "";
		File readmeFile = new File(challengeDirectory, "assets/README.md");
		if (readmeFile.exists()) {
			try {
				markdown = FileUtils.readFileToString(readmeFile, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new Exception("Description couldn't be loaded from " + readmeFile.getName(), e);
			}
		} else {
			markdown = Teuton.readme(challengeDirectory);
		}
		String html = "";
		try {
			html = MarkdownUtils.render(markdown);
		} catch (IOException e) {
			throw new Exception("Error rendering " + readmeFile.getName() + " description file", e);
		}
		return html;
	}
	
	public void start() {
		new Thread(this).start();
	}

}
