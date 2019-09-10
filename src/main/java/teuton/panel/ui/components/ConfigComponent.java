package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ConfigComponent extends BorderPane implements Initializable {

	// model

	private MapProperty<String, Object> config = new SimpleMapProperty<String, Object>(
			FXCollections.observableHashMap());

	// view

	@FXML
	private GridPane propertiesPane;

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

	private void onConfigChanged(ObservableValue<? extends ObservableMap<String, Object>> o, ObservableMap<String, Object> ov, ObservableMap<String, Object> nv) {

		propertiesPane.getChildren().clear();
		
		
		int i = 0;
		for (String name : nv.keySet()) {
			
			Label nameLabel = new Label(name + ":");

			Node valueNode;
			if (nv.get(name) instanceof Boolean) {
				CheckBox valueCheck = new CheckBox();
				valueCheck.setSelected((Boolean)nv.get(name));
				valueCheck.setDisable(true);
				valueNode = valueCheck;
			} else {
				Label valueLabel = new Label("" + nv.get(name));
				valueLabel.setStyle("-fx-font-weight: bold");
				valueNode = valueLabel;
			}
			
			propertiesPane.addRow(i++, nameLabel, valueNode);
		}

	}

	public final MapProperty<String, Object> configProperty() {
		return this.config;
	}

	public final ObservableMap<String, Object> getConfig() {
		return this.configProperty().get();
	}

	public final void setConfig(final ObservableMap<String, Object> config) {
		this.configProperty().set(config);
	}

}
