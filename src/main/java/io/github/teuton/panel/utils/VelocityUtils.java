package io.github.teuton.panel.utils;

import java.util.Arrays;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.util.StringBuilderWriter;

public class VelocityUtils {

	private static VelocityEngine velocityEngine;

	static {
		velocityEngine = new VelocityEngine();
		
		// specify resource loaders to use
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
		
		// for the loader 'class', set the ClasspathResourceLoader as the class to use
		velocityEngine.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		velocityEngine.init();
	}

	@SafeVarargs
	public static String render(String templateName, Pair<String, Object> ... values) {
		VelocityContext context = new VelocityContext();
		Arrays.asList(values).stream().forEach(v -> context.put(v.getKey(), v.getValue()));
		Template template = velocityEngine.getTemplate(templateName, "UTF-8");
		StringBuilderWriter writer = new StringBuilderWriter();
		template.merge(context, writer);
		return writer.toString();
	}

}
