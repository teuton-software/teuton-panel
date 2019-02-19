package teuton.panel.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

/**
 * Extraer el contenido de recursos locales a partir de su URL.
 * @author fvarrui
 */
public class ResourceUtils {
	
	public static String readResource(String path) {
		String content = "";
		try {
			content = FileUtils.readFileToString(new File(ResourceUtils.class.getResource(path).toURI()), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return content;
	}

}
