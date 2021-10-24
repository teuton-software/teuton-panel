package io.github.teuton.panel.ui.panes;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.model.resume.Resume;
import io.github.teuton.panel.ui.utils.FXUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class ResumePane extends BorderPane implements Initializable {

	// model

	private ObjectProperty<Resume> resume = new SimpleObjectProperty<>();
	
	// components
	
	private ListPane configComponent;
	private ListPane resultsComponent;
	private TablePane casesComponent;

	// view
	
    @FXML
    private ScrollPane configPane;

    @FXML
    private ScrollPane resultsPane;

    @FXML
    private BorderPane bottomPane;

	public ResumePane() {
		FXUtils.load("/fxml/Resume.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize view
		
		configComponent = new ListPane("resume.config.order");
		configPane.setContent(configComponent);

		resultsComponent = new ListPane("resume.results.order");
		resultsPane.setContent(resultsComponent);

		casesComponent = new TablePane("resume.cases.order");
		bottomPane.setCenter(casesComponent);
		
		// listeners
		
		resume.addListener((o, ov, nv) -> onResumeChanged(o, ov, nv));
		
	}

	// listeners
	
	private void onResumeChanged(ObservableValue<? extends Resume> o, Resume ov, Resume nv) {
		if (nv == null) {
			configComponent.itemsProperty().unbind();
			resultsComponent.itemsProperty().unbind();
			casesComponent.itemsProperty().unbind();
		} else {
			configComponent.itemsProperty().bind(nv.configProperty());
			resultsComponent.itemsProperty().bind(nv.resultsProperty());
			casesComponent.itemsProperty().bind(nv.casesProperty());
		}
	}
	
	// properties

	public final ObjectProperty<Resume> resumeProperty() {
		return this.resume;
	}

	public final Resume getResume() {
		return this.resumeProperty().get();
	}

	public final void setResume(final Resume resume) {
		this.resumeProperty().set(resume);
	}

}
