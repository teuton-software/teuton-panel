package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import teuton.panel.ui.model.Config;

public class ConfigComponent extends BorderPane implements Initializable {

	private ObjectProperty<Config> config = new SimpleObjectProperty<>();

	public ConfigComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Config.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		config.addListener((o, ov, nv) -> onConfigChanged(o, ov, nv));

	}

	private void onConfigChanged(ObservableValue<? extends Config> o, Config ov, Config nv) {
		if (ov != null) {
			// TODO unbind
		}
		if (nv != null) {
			// TODO bind
		}
	}

	public final ObjectProperty<Config> configProperty() {
		return this.config;
	}

	public final Config getConfig() {
		return this.configProperty().get();
	}

	public final void setConfig(final Config config) {
		this.configProperty().set(config);
	}

}
