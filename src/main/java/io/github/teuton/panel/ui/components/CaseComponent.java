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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class CaseComponent extends BorderPane implements Initializable {

	// model

	private ObjectProperty<Case> _case;

	// view

	private ListComponent configComponent;
	private GroupsComponent groupComponent;
	private ListComponent resultsComponent;

	@FXML
	private Tab configTab, groupsTab, resultsTab, logsTab;
	
    @FXML
    private Label caseLabel;

    @FXML
    private Label membersLabel;
    
    @FXML
    private ScrollPane resultsContentPane, configContentPane;

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

		configComponent = new ListComponent("case.config.order");
		configComponent.setPadding(new Insets(5));
		
		groupComponent = new GroupsComponent();
		
		resultsComponent = new ListComponent("case.results.order");
		resultsComponent.setPadding(new Insets(5));

		configContentPane.setContent(configComponent);
		groupsTab.setContent(groupComponent);
		resultsContentPane.setContent(resultsComponent);

	}

	private void onCaseChanged(ObservableValue<? extends Case> o, Case ov, Case nv) {
		
		caseLabel.setText("");
		membersLabel.setText("");
		
		if (ov != null) {
			configComponent.itemsProperty().unbind();
			groupComponent.groupsProperty().unbind();
			resultsComponent.itemsProperty().unbind();
		}
		
		if (nv != null) {
			caseLabel.setText("Case " + getCase().getResults().get("case_id"));
			membersLabel.setText(getCase().getConfig().get("tt_members").toString());
			configComponent.itemsProperty().bind(nv.configProperty());
			groupComponent.groupsProperty().bind(nv.groupsProperty());
			resultsComponent.itemsProperty().bind(nv.resultsProperty());
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
