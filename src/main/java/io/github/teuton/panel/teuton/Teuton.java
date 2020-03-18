package io.github.teuton.panel.teuton;

import java.io.StringWriter;

import javax.script.ScriptException;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

public class Teuton {

	public static void main(String[] args) throws ScriptException {
		System.out.println(execute(args));
	}
	
	public static String execute(String ... args) {
		return ruby("teuton", args);
	}

	@SuppressWarnings("unchecked")
	public static String ruby(String rubyfile, String ... args) {
		StringWriter writer = new StringWriter();
		ScriptingContainer container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
		container.getEnvironment().put("GEM_PATH", "classpath:/rubygems");
		container.setArgv(args);
		container.setOutput(writer);
		container.runScriptlet(PathType.CLASSPATH, rubyfile);
		return writer.toString();
	}
	
}
