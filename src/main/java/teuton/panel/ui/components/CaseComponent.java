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
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import teuton.panel.ui.model.Case;

public class CaseComponent extends TabPane implements Initializable {

	// model

	private ObjectProperty<Case> _case;

	// view

	private ConfigComponent configComponent;
	private TargetComponent targetsComponent;
	private ResultsComponent resultsComponent;

	@FXML
	private Accordion testsPane;

	@FXML
	private Tab configTab, targetsTab, resultsTab;

	public CaseComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Case.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		_case = new SimpleObjectProperty<>();
		_case.addListener((o, ov, nv) -> onCaseChanged(o, ov, nv));

		configComponent = new ConfigComponent();
		targetsComponent = new TargetComponent();
		resultsComponent = new ResultsComponent();

		configTab.setContent(configComponent);
		targetsTab.setContent(targetsComponent);
		resultsTab.setContent(resultsComponent);

	}

	private void onCaseChanged(ObservableValue<? extends Case> o, Case ov, Case nv) {
		if (ov != null) {
			configComponent.configProperty().unbind();
			targetsComponent.testProperty().unbind();
			resultsComponent.resultsProperty().unbind();
		}
		if (nv != null) {
			configComponent.configProperty().bind(nv.configProperty());
			targetsComponent.testProperty().bind(nv.testProperty());
			resultsComponent.resultsProperty().bind(nv.resultsProperty());
		}
	}

	public final ObjectProperty<Case> caseProperty() {
		return this._case;
	}

	public final Case getCase() {
		return this.caseProperty().get();
	}

	public final void setCase(final Case _case) {
		this.caseProperty().set(_case);
	}

}
