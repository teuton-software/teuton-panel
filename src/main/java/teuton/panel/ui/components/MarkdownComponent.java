package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class MarkdownComponent extends BorderPane implements Initializable {

	// model

	private StringProperty markdown;

	// view

	@FXML
	private WebView webView;

	public MarkdownComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Markdown.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		markdown = new SimpleStringProperty();
		markdown.addListener((o, ov, nv) -> {
			webView.getEngine().loadContent(nv, "text/html");
		});

	}

	public final StringProperty markdownProperty() {
		return this.markdown;
	}

	public final String getMarkdown() {
		return this.markdownProperty().get();
	}

	public final void setMarkdown(final String markdown) {
		this.markdownProperty().set(markdown);
	}

}
