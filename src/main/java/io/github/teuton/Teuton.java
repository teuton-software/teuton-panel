package io.github.teuton;

import java.io.File;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

public class Teuton {
	
	private static final Pattern VERSION_PATTERN = Pattern.compile("^teuton *\\(version *(.*)\\)$");

	@SuppressWarnings("unchecked")
	public static String ruby(String rubyfile, File currentDirectory, String ... args) {
		StringWriter writer = new StringWriter();
		ScriptingContainer container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
		container.setCurrentDirectory(currentDirectory.getAbsolutePath());
		container.getEnvironment().put("GEM_PATH", "classpath:/rubygems");
		container.setArgv(args);
		container.setOutput(writer);
		container.runScriptlet(PathType.CLASSPATH, rubyfile);
		return writer.toString();
	}

	public static String execute(File currentDirectory, String ... args) {
		return ruby("rubygems/bin/teuton", currentDirectory, args);
	}

	public static String execute(String ... args) {
		return execute(new File("."), args);
	}
	
	public static String getVersion() {
		String output = execute("version");
		Matcher m = VERSION_PATTERN.matcher(output.trim());
		if (m.matches()) {
			return m.group(1);
		}
		return null;
	}
	
	public static String play(File challengeDirectory) {
		return Teuton.execute(challengeDirectory, "play", "--export=json", ".");		
	}

	public static String readme(File challengeDirectory) {
		return Teuton.execute(challengeDirectory, "readme", ".");		
	}
	
}
