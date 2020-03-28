package io.github.teuton;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

public class Teuton {
	
	private static final Pattern VERSION_PATTERN = Pattern.compile("^teuton *\\(version *(.*)\\)$");

	@SuppressWarnings("unchecked")
	private static String ruby(String rubyfile, File currentDirectory, String ... args) {
		StringWriter writer = new StringWriter();
		ScriptingContainer container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
		container.setCurrentDirectory(currentDirectory.getAbsolutePath());
		container.getEnvironment().put("GEM_PATH", "classpath:/rubygems");
		container.setArgv(args);
		container.setOutput(writer);
		container.runScriptlet(PathType.CLASSPATH, rubyfile);
		return writer.toString();
	}

	private static String execute(File currentDirectory, String ... args) {
		return ruby("rubygems/bin/teuton", currentDirectory, args);
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

	public static String play(File challengeDirectory) {
		return play(challengeDirectory, null, null);
	}
	
	public static String play(File challengeDirectory, File configFile, Integer[] cases) {
		List<String> args = new ArrayList<>();
		args.add("play");
		args.add("--export=json");
		if (configFile != null) args.add("--cpath=" + configFile.getAbsolutePath());
		if (cases != null && cases.length > 0) args.add("--case=" + StringUtils.join(cases, ","));
		args.add(".");
		return execute(challengeDirectory, args.toArray(new String[args.size()]));		
	}

	public static String readme(File challengeDirectory) {
		return execute(challengeDirectory, "readme", ".");
	}

	public static void main(String[] args) {
		System.out.println(execute("help", "play"));
	}
	
}
