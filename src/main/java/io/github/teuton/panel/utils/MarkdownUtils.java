package io.github.teuton.panel.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
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
				)
			);
		
		parser = Parser.builder(options).build();
		renderer = HtmlRenderer.builder(options).build();
	}

	/**
	 * Render Markdown string into HTML
	 * @param markdown
	 * @return html
	 * @throws IOException 
	 */
	public static String render(String markdown) throws IOException {
		Document document = parser.parse(markdown);
		String body = renderer.render(document);
		String html = VelocityUtils.render(
				"/templates/html.vtl", 
				Pair.of("body", body),
				Pair.of("javascript", IOUtils.resourceToString("/markdown/js/markdown.min.js", Charset.forName("UTF-8"))),
				Pair.of("css", IOUtils.resourceToString("/markdown/css/markdown.min.css", Charset.forName("UTF-8")))
			);
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
			FileUtils.write(temp, html, Charset.forName("UTF-8"));
			return temp.toURI().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
