package io.github.teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class MapComponent extends BorderPane implements Initializable {
	
	private static final ResourceBundle TITLES = ResourceBundle.getBundle("titles");

	// model

	private MapProperty<String, Object> map = new SimpleMapProperty<String, Object>(
			FXCollections.observableHashMap());

	// view

	@FXML
	private GridPane propertiesPane;

	public MapComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		map.addListener((o, ov, nv) -> onMapChanged(o, ov, nv));

	}

	private void onMapChanged(ObservableValue<? extends ObservableMap<String, Object>> o, ObservableMap<String, Object> ov, ObservableMap<String, Object> nv) {

		propertiesPane.getChildren().clear();
		
		int i = 0;
		for (String name : nv.keySet()) {
			
			String title = name;
			try {
				title = TITLES.getString(name);
			} catch (MissingResourceException e) {
				title = StringUtils.capitalize(name.replaceAll("_", " "));
			}
			Label nameLabel = new Label(title + ":");

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
			
			RowConstraints constraint = new RowConstraints();
			constraint.setMinHeight(20);
			constraint.setPrefHeight(20);
			constraint.setVgrow(Priority.NEVER);
			
			propertiesPane.getRowConstraints().add(i, constraint);
			propertiesPane.addRow(i, nameLabel, valueNode);
			
			i++;
		}

	}

	public final MapProperty<String, Object> mapProperty() {
		return this.map;
	}

	public final ObservableMap<String, Object> getMap() {
		return this.mapProperty().get();
	}

	public final void setMap(final ObservableMap<String, Object> config) {
		this.mapProperty().set(config);
	}

}
