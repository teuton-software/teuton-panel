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
import javafx.scene.control.TitledPane;
import teuton.panel.ui.model.Target;

public class TargetComponent extends TitledPane implements Initializable {
	
	// model

	private ObjectProperty<Target> target = new SimpleObjectProperty<Target>();
	
	// view

    @FXML
    private Label idLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label commandLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label alterationsLabel;

    @FXML
    private Label expectedLabel;

    @FXML
    private Label resultLabel;

	public TargetComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Target.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		target.addListener((o, ov, nv) -> onTargetChanged(o, ov, nv));
		
	}
	
	// listeners

	private void onTargetChanged(ObservableValue<? extends Target> o, Target ov, Target nv) {
		if (ov != null) {
			textProperty().unbind();
		    idLabel.textProperty().unbind();
		    scoreLabel.textProperty().unbind();
		    weightLabel.textProperty().unbind();
		    commandLabel.textProperty().unbind();
		    durationLabel.textProperty().unbind();
		    alterationsLabel.textProperty().unbind();
		    expectedLabel.textProperty().unbind();
		    resultLabel.textProperty().unbind();
		}
		if (nv != null) {
			textProperty().bind(nv.descriptionProperty());
		    idLabel.textProperty().bind(nv.targetIdProperty());
		    scoreLabel.textProperty().bind(nv.scoreProperty().asString("%.2f"));
		    weightLabel.textProperty().bind(nv.weightProperty().asString("%.2f"));
		    commandLabel.textProperty().bind(nv.commandProperty());
		    durationLabel.textProperty().bind(nv.durationProperty().asString("%.5f"));
		    alterationsLabel.textProperty().bind(nv.alterationsProperty());
		    expectedLabel.textProperty().bind(nv.expectedProperty());
		    resultLabel.textProperty().bind(nv.resultProperty());
		}
	}

	// properties
	
	public final ObjectProperty<Target> targetProperty() {
		return this.target;
	}

	public final Target getTarget() {
		return this.targetProperty().get();
	}

	public final void setTarget(final Target target) {
		this.targetProperty().set(target);
	}

}
