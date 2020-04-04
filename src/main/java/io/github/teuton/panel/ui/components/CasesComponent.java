package io.github.teuton.panel.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import io.github.teuton.panel.ui.model.cases.Case;
import io.github.teuton.panel.ui.utils.FXUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class CasesComponent extends SplitPane implements Initializable {

	// ===================================
	// model
	// ===================================

	private ListProperty<Case> cases;

	// ===================================
	// view
	// ===================================

	private CaseComponent caseComponent;
	private WarningComponent warningComponent;

	@FXML
	private JFXListView<Case> casesList;

	@FXML
	private BorderPane casePane;

	// ===================================
	// constructor
	// ===================================

	public CasesComponent() {
		FXUtils.load("/fxml/Cases.fxml", this);
	}

	// ===================================
	// initialization
	// ===================================

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// model

		cases = new SimpleListProperty<>(FXCollections.observableArrayList());

		// view

		warningComponent = new WarningComponent();
		casePane.setCenter(warningComponent);

		caseComponent = new CaseComponent();

		casesList.setCellFactory(v -> new CaseListCell());
		
		// bindings

		warningComponent.messageProperty().bind(
				Bindings
					.when(cases.emptyProperty())
					.then("Press 'Play' button to run the test")
					.otherwise("Select a case from the list on the left")
				);
		
		casesList.itemsProperty().bind(cases);
		
		// listeners 
		
		casesList.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> onCaseSelectionChanged(ov, nv));

	}

	// ===================================
	// listeners
	// ===================================

	private void onCaseSelectionChanged(Case ov, Case nv) {
		if (nv == null) {
			casePane.setCenter(warningComponent);
			caseComponent.caseProperty().unbind();
		} else {
			casePane.setCenter(caseComponent);
			caseComponent.caseProperty().bind(casesList.getSelectionModel().selectedItemProperty());
		}
	}

	// ===================================
	// properties
	// ===================================

	public final ListProperty<Case> casesProperty() {
		return this.cases;
	}

	public final ObservableList<Case> getCases() {
		return this.casesProperty().get();
	}

	public final void setCases(final ObservableList<Case> cases) {
		this.casesProperty().set(cases);
	}

}
