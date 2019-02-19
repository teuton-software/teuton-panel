package teuton.panel.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace variables in ${name} format for its values (system variables).
 * @author fvarrui
 */
public class Translator {
	
	private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([a-zA-Z0-9\\.]+)\\}");
	
	public static String translate(String string, Map<String, String> map) {
		Matcher m = VARIABLE_PATTERN.matcher(string);
		while (m.find()) {
			String variable = Pattern.quote(m.group());
			String name = m.group(1);
			if (map.get(name) == null) continue;
			String value = Matcher.quoteReplacement(map.get(name).toString());
			string = string.replaceAll(variable, value);
		}
		return string;
	}
	
	public static Map<String, String> translate(Map<String, String> map) {
		Map<String, String> translatedMap = new HashMap<>(map);
		for (String key : translatedMap.keySet()) {
			Object value = translatedMap.get(key);
			if (value instanceof String) {
				String newValue = translate(value.toString(), translatedMap);
				translatedMap.put(key, newValue);
			}
		}
		return translatedMap;
	}
	
	public static Map<String, String> getMapFromProperties(Properties p) {
		return populateMapFromProperties(new HashMap<>(), p);
	}
	
	public static Map<String, String> populateMapFromProperties(Map<String, String> data, Properties p) {
		p.stringPropertyNames().stream().forEach(key -> data.put(key, p.getProperty(key)));
		return data;
	}

}
