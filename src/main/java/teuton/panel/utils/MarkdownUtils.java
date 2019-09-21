package teuton.panel.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * Utils to render Markdown
 * @author fvarrui
 */
public class MarkdownUtils {

	private static final String TEMPLATE_BEGIN = 
			"<html>" + "\n" + 
				"<head>" + "\n" + 
					"<script>" + ResourceUtils.readResource("/markdown/js/markdown.js") + "</script>" + "\n" + 
					"<style>" + ResourceUtils.readResource("/markdown/css/markdown.css") + "</style>" + "\n" + 
				"</head>" + "\n" + 
				"<body>";

	private static final String TEMPLATE_END = 
				"</body>" + "\n" + 
			"</html>";

	private static Parser parser;
	private static HtmlRenderer renderer;

	static {
		
		MutableDataSet options = new MutableDataSet();
		options.setFrom(ParserEmulationProfile.KRAMDOWN);
		options.set(Parser.EXTENSIONS, Arrays.asList(
				AbbreviationExtension.create(), 
				DefinitionExtension.create(), 
				FootnoteExtension.create(),
				TablesExtension.create(), 
				TypographicExtension.create(), 
				StrikethroughExtension.create()
//				YamlFrontMatterExtension.create()
				)
			);
		parser = Parser.builder(options).build();
		renderer = HtmlRenderer.builder(options).build();
	}

	/**
	 * Render Markdown string into HTML
	 * @param markdown
	 * @return html
	 */
	public static String render(String markdown) {
		Document document = parser.parse(markdown);
		String body = renderer.render(document);
		String html = TEMPLATE_BEGIN + body + TEMPLATE_END;
		return html;
	}
	
	/**
	 * Render Markdown string to a temporary file
	 * @param markdown
	 * @return url to temporary file
	 */
	public static String renderToUrl(String markdown) {
		try {
			File temp = File.createTempFile("teuton-panel-", ".html");
			String html = render(markdown);
			FileUtils.write(temp, html, Charset.defaultCharset());
			return temp.toURI().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
