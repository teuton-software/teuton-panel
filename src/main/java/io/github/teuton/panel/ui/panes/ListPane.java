package io.github.teuton.panel.ui.panes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

public class ListPane extends BorderPane implements Initializable {

	private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

	private String[] order;

	// model

	private MapProperty<String, Object> items = new SimpleMapProperty<String, Object>(FXCollections.observableHashMap());

	// view

	@FXML
	private GridPane propertiesPane;

	public ListPane(String orderProperty) {
		this.order = CONFIG.getString(orderProperty).split(",");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/List.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		items.addListener((o, ov, nv) -> onMapChanged(o, ov, nv));

	}

	private String getTitle(String name) {
		try {
			return CONFIG.getString(name + ".title");
		} catch (MissingResourceException e) {
			return StringUtils.capitalize(name.replaceAll("_", " "));
		}
	}

	private String getStyle(String name) {
		try {
			return CONFIG.getString(name + ".style");
		} catch (MissingResourceException e) {
			return "";
		}
	}

	private String getFormat(String name) {
		try {
			return CONFIG.getString(name + ".format");
		} catch (MissingResourceException e) {
			return "%s";
		}
	}

	private void onMapChanged(ObservableValue<? extends ObservableMap<String, Object>> o,
			ObservableMap<String, Object> ov, ObservableMap<String, Object> nv) {

		if (ov != null) {
			propertiesPane.getChildren().clear();
		}

		if (nv != null) {
			List<String> order = Arrays.asList(this.order);
			List<String> keys = new ArrayList<>(nv.keySet());
			keys.removeAll(order);
			List<String> fields = Stream.of(order, keys).flatMap(x -> x.stream()).collect(Collectors.toList());

			int i = 0;
			for (String name : fields) {

				if (!nv.containsKey(name))
					continue;

				// property name column
				String title = getTitle(name);
				Label nameLabel = new Label(title + ":");

				// property value column
				String styleClass = getStyle(name);
				String format = getFormat(name);
				Object value = nv.get(name);
				Node valueNode;
				if (value instanceof Boolean) {
					CheckBox valueCheck = new CheckBox();
					valueCheck.setSelected((Boolean) value);
					valueCheck.setDisable(true);
					valueNode = valueCheck;
				} else {
					Label valueLabel = new Label();
					valueLabel.setStyle("-fx-font-weight: bold");
					valueLabel.setText(String.format(format, value));
					valueNode = valueLabel;
				}
				valueNode.getStyleClass().add(styleClass);

				// constraint
				RowConstraints constraint = new RowConstraints();
				constraint.setMinHeight(20);
				constraint.setPrefHeight(20);
				constraint.setVgrow(Priority.NEVER);

				// add property name, property value and constraints to grid pane
				propertiesPane.getRowConstraints().add(i, constraint);
				propertiesPane.addRow(i, nameLabel, valueNode);

				i++;
			}

		}

	}

	public final MapProperty<String, Object> itemsProperty() {
		return this.items;
	}

	public final ObservableMap<String, Object> getItems() {
		return this.itemsProperty().get();
	}

	public final void setItems(final ObservableMap<String, Object> items) {
		this.itemsProperty().set(items);
	}

}
