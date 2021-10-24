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

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.BorderPane;

public class TablePane extends BorderPane implements Initializable {

	private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

	private String[] order;

	// model

	private ListProperty<MapProperty<String, Object>> items = new SimpleListProperty<>(
			FXCollections.observableArrayList());

	// view

	@FXML
	private TableView<MapProperty<String, Object>> tableView;

	public TablePane(String orderProperty) {
		this.order = CONFIG.getString(orderProperty).split(",");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Table.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		items.addListener((o, ov, nv) -> onItemsChanged(o, ov, nv));
	}

	private String getTitle(String name) {
		try {
			return CONFIG.getString(name + ".title");
		} catch (MissingResourceException e) {
			return StringUtils.capitalize(name.replaceAll("_", " "));
		}
	}

	private String getFormat(String name) {
		try {
			return CONFIG.getString(name + ".format");
		} catch (MissingResourceException e) {
			return "%s";
		}
	}

	private void onItemsChanged(ObservableValue<? extends ObservableList<MapProperty<String, Object>>> o,
			ObservableList<MapProperty<String, Object>> ov, ObservableList<MapProperty<String, Object>> nv) {

		if (ov != null) {
			tableView.getItems().clear();
			tableView.getColumns().clear();
		}

		if (nv != null && !nv.isEmpty()) {

			// sets columns order
			MapProperty<String, Object> firstItem = nv.get(0);
			List<String> order = Arrays.asList(this.order);
			List<String> keys = new ArrayList<>(firstItem.keySet());
			keys.removeAll(order);
			List<String> fields = Stream.of(order, keys).flatMap(x -> x.stream()).collect(Collectors.toList());
			
			// creates columns
			for (String name : fields) {
				
				if (!firstItem.containsKey(name)) continue;
				
				String title = getTitle(name);
				String format = getFormat(name);

				TableColumn<MapProperty<String, Object>, ?> column;
				if (firstItem.get(name) instanceof Boolean) {
					TableColumn<MapProperty<String,Object>, Boolean> c = new TableColumn<>();
					c.setCellValueFactory(v -> new SimpleBooleanProperty((Boolean) v.getValue().get(name)));
					c.setCellFactory(CheckBoxTableCell.forTableColumn(c));
					column = c;
				} else {
					TableColumn<MapProperty<String,Object>, String> c = new TableColumn<>();
					c.setCellValueFactory(v -> new SimpleStringProperty(String.format(format, v.getValue().get(name))));
					column = c;
				}
				column.setEditable(false);
				column.setText(title);
				
				tableView.getColumns().add(column);
				
			}
			
			tableView.setItems(nv);

		}

	}

	public final ListProperty<MapProperty<String, Object>> itemsProperty() {
		return this.items;
	}

	public final ObservableList<MapProperty<String, Object>> getItems() {
		return this.itemsProperty().get();
	}

	public final void setItems(final ObservableList<MapProperty<String, Object>> items) {
		this.itemsProperty().set(items);
	}

}
