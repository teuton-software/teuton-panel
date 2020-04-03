package io.github.teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class MarkdownComponent extends BorderPane implements Initializable {

	// model

	private BooleanProperty loading;
	private StringProperty markdown;

	// view

	@FXML
	private WebView webView;

	@FXML
	private BorderPane loadingPane;
	
	@FXML
	private Label loadingLabel;

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

		webView.setContextMenuEnabled(false);
		
		loading = new SimpleBooleanProperty();

		markdown = new SimpleStringProperty();
		markdown.addListener((o, ov, nv) -> {
			webView.getEngine().loadContent(nv, "text/html");
		});
		
		loadingPane.visibleProperty().bind(loading);

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

	public final BooleanProperty loadingProperty() {
		return this.loading;
	}

	public final boolean isLoading() {
		return this.loadingProperty().get();
	}

	public final void setLoading(final boolean loading) {
		this.loadingProperty().set(loading);
	}

}
