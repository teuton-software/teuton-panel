package io.github.teuton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

import io.github.teuton.panel.utils.StreamCharacterConsumer;

public class Teuton {
	
	private static final String TEUTON_PATH = "rubygems/bin/teuton";
	private static final Pattern VERSION_PATTERN = Pattern.compile("^teuton *\\(version *(.*)\\)$");
	
	@SuppressWarnings("unchecked")
	private static String ruby(Writer writer, String rubyfile, File currentDirectory, String ... args) {
		System.out.println("ruby: " + rubyfile + " " + StringUtils.join(args, " "));
		ScriptingContainer container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
		container.setCurrentDirectory(currentDirectory.getAbsolutePath());
		container.getEnvironment().put("GEM_PATH", "classpath:/rubygems");
		container.setArgv(args);
		container.setOutput(writer);
		container.setError(writer);
		container.runScriptlet(PathType.CLASSPATH, rubyfile);
		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	private static InputStream ruby(String rubyfile, File currentDirectory, String ... args) throws IOException {
		PipedInputStream pis = new PipedInputStream();
		PipedOutputStream pos = new PipedOutputStream(pis);
		Writer writer = new OutputStreamWriter(pos);
		Thread t = new Thread(() -> {
			ScriptingContainer container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
			container.setCurrentDirectory(currentDirectory.getAbsolutePath());
			container.getEnvironment().put("GEM_PATH", "classpath:/rubygems");
			container.setArgv(args);
			container.setOutput(writer);
			container.setError(writer);
			container.runScriptlet(PathType.CLASSPATH, rubyfile);
		});
		t.start();
		return pis;
	}

	private static String execute(File currentDirectory, String ... args) {
		return ruby(new StringWriter(), TEUTON_PATH, currentDirectory, args);
	}

	private static String execute(String ... args) {
		return execute(new File("."), args);
	}
	
	public static String version() {
		String output = execute("version");
		Matcher m = VERSION_PATTERN.matcher(output.trim());
		if (m.matches()) {
			return m.group(1);
		}
		return null;
	}
	
	public static InputStream play(File challengeDirectory, File configFile, Integer[] cases) throws IOException {
		List<String> args = new ArrayList<>();
		args.add("play");
		if (configFile != null) args.add("--cpath=" + configFile.getAbsolutePath());
		if (cases != null && cases.length > 0) args.add("--case=" + StringUtils.join(cases, ","));
		args.add("--export=json");
		args.add(".");
		return ruby(TEUTON_PATH, challengeDirectory, args.toArray(new String[args.size()]));		
	}

	public static String readme(File challengeDirectory) {
		return execute(challengeDirectory, "readme", ".");
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		File challenge = new File("C:\\Users\\fvarrui\\GitHub\\teuton-panel\\samples\\windows-test");
		InputStream output = play(challenge, null, null);
		new StreamCharacterConsumer(output, (c) -> {
			System.out.print(c);
		}).start();
		
//		System.out.println(version());

//		System.out.println(execute("help", "play"));
		
//		System.out.println(execute("new", "samples/test"));
		
//		is.close();
		
	}
	
}
