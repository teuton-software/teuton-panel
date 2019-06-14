package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import teuton.panel.ui.model.Results;

public class ResultsComponent extends BorderPane implements Initializable {

	// model

	private ObjectProperty<Results> results = new SimpleObjectProperty<>();

	// view

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label finishTimeLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label uniqueFaultLabel;

    @FXML
    private Label maxWeightLabel;

    @FXML
    private Label goodWeightLabel;

    @FXML
    private Label failWeightLabel;

    @FXML
    private Label failCounterLabel;

    @FXML
    private Label gradeLabel;

	public ResultsComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Results.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		results.addListener((o, ov, nv) -> onResultsChanged(o, ov, nv));

	}

	private void onResultsChanged(ObservableValue<? extends Results> o, Results ov, Results nv) {
		if (ov != null) {
			startTimeLabel.textProperty().unbind();
			finishTimeLabel.textProperty().unbind();
			durationLabel.textProperty().unbind();
			uniqueFaultLabel.textProperty().unbind();
			maxWeightLabel.textProperty().unbind();
			goodWeightLabel.textProperty().unbind();
			failWeightLabel.textProperty().unbind();
			failCounterLabel.textProperty().unbind();
			gradeLabel.textProperty().unbind();
		}
		if (nv != null) {
			startTimeLabel.textProperty().bind(nv.startTimeProperty());
			finishTimeLabel.textProperty().bind(nv.finishTimeProperty());
			durationLabel.textProperty().bind(nv.durationProperty().asString("%.5f sec"));
			uniqueFaultLabel.textProperty().bind(nv.uniqueFaultProperty().asString());
			maxWeightLabel.textProperty().bind(nv.maxWeightProperty().asString("%.2f"));
			goodWeightLabel.textProperty().bind(nv.goodWeightProperty().asString("%.2f"));
			failWeightLabel.textProperty().bind(nv.failWeightProperty().asString("%.2f"));
			failCounterLabel.textProperty().bind(nv.failCounterProperty().asString());
			gradeLabel.textProperty().bind(nv.gradeProperty().asString("%d/100"));
		}
	}

	public final ObjectProperty<Results> resultsProperty() {
		return this.results;
	}

	public final Results getResults() {
		return this.resultsProperty().get();
	}

	public final void setResults(final Results results) {
		this.resultsProperty().set(results);
	}

}
