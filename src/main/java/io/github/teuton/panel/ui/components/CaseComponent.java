package io.github.teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.model.cases.Case;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CaseComponent extends TabPane implements Initializable {

	// model

	private ObjectProperty<Case> _case;

	// view

	private MapComponent configComponent;
	private GroupComponent groupComponent;
	private MapComponent resultsComponent;

	@FXML
	private Accordion testsPane;

	@FXML
	private Tab configTab, groupsTab, resultsTab, logsTab;

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

		configComponent = new MapComponent();
		configComponent.setPadding(new Insets(5));
		
		groupComponent = new GroupComponent();
		
		resultsComponent = new MapComponent();
		resultsComponent.setPadding(new Insets(5));

		configTab.setContent(configComponent);
		groupsTab.setContent(groupComponent);
		resultsTab.setContent(resultsComponent);

	}

	private void onCaseChanged(ObservableValue<? extends Case> o, Case ov, Case nv) {
		if (ov != null) {
			configComponent.mapProperty().unbind();
			groupComponent.testProperty().unbind();
			resultsComponent.mapProperty().unbind();
		}
		if (nv != null) {
			configComponent.mapProperty().bind(nv.configProperty());
			groupComponent.testProperty().bind(nv.testProperty());
			resultsComponent.mapProperty().bind(nv.resultsProperty());
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
