package io.github.teuton.panel.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.utils.FXUtils;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class HallOfFameComponent extends VBox implements Initializable {

	// model

	private MapProperty<String, String> hallOfFame = new SimpleMapProperty<String, String>(FXCollections.observableHashMap());

	// view

	@FXML
	private BarChart<String, Integer> chart;

	public HallOfFameComponent() {
		FXUtils.load("/fxml/HallOfFame.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hallOfFame.addListener((o, ov, nv) -> onHallOfFameChanged(o, ov, nv));
	}

	private void onHallOfFameChanged(ObservableValue<? extends ObservableMap<String, String>> o, ObservableMap<String, String> ov, ObservableMap<String, String> nv) {
		chart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        series.setName("Hall of fame");
        for (String key : nv.keySet()) {
        	int value = nv.get(key).length();
	        series.getData().add(new XYChart.Data<String, Integer>(key, value));
        }
        chart.getData().add(series);
	}

	public final MapProperty<String, String> hallOfFameProperty() {
		return this.hallOfFame;
	}

	public final ObservableMap<String, String> getHallOfFame() {
		return this.hallOfFameProperty().get();
	}

	public final void setHallOfFame(final ObservableMap<String, String> hallOfFame) {
		this.hallOfFameProperty().set(hallOfFame);
	}

}
