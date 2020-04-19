package io.github.teuton.panel.ui.settings;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.SystemUtils;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import io.github.teuton.Teuton;
import io.github.teuton.panel.ui.model.Settings;
import io.github.teuton.panel.ui.utils.Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class SettingsController extends Controller<BorderPane> {

	// model

	private ReadOnlyObjectWrapper<Settings> settings;

	// view
	private PopOver popOver;

	@FXML
	private Label usernameLabel, osLabel, appVersionLabel, teutonVersionLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// set initial app settings
		Settings settings = new Settings();
		settings.setOs(String.format("%s (%s)", SystemUtils.OS_NAME, SystemUtils.OS_VERSION));
		settings.setTeutonVersion(Teuton.version());
		settings.setAppVersion(ResourceBundle.getBundle("teuton-panel").getString("teuton.panel.version"));
		settings.setUsername(System.getProperty("user.name"));

		this.settings = new ReadOnlyObjectWrapper<>(settings);

		// config popup to show settings
		popOver = new PopOver(getRoot());
		popOver.setTitle("Settings");
		popOver.setArrowLocation(ArrowLocation.TOP_RIGHT);
		popOver.setCloseButtonEnabled(true);
		popOver.setHeaderAlwaysVisible(true);
		popOver.setDetachable(false);
		popOver.setCornerRadius(2);
		popOver.setAutoHide(true);
		popOver.setAutoFix(false);
		popOver.setFadeInDuration(Duration.millis(1));
		popOver.setFadeOutDuration(Duration.millis(1));

		// bind view to model
		osLabel.textProperty().bind(settings.osProperty());
		appVersionLabel.textProperty().bind(settings.appVersionProperty());
		usernameLabel.textProperty().bind(settings.usernameProperty());
		teutonVersionLabel.textProperty().bind(
				Bindings
					.when(settings.teutonVersionProperty().isNotEmpty())
					.then(settings.teutonVersionProperty()).otherwise("Not installed")
			);

	}

	public void showPopOver(Node owner) {
		if (!popOver.isShowing())
			popOver.show(owner);
		else
			popOver.hide();
	}

	public final ReadOnlyObjectProperty<Settings> settingsProperty() {
		return this.settings.getReadOnlyProperty();
	}

	public final Settings getSettings() {
		return this.settingsProperty().get();
	}

}
