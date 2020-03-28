package io.github.teuton.panel.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.model.resume.Case;
import io.github.teuton.panel.ui.model.resume.Resume;
import io.github.teuton.panel.utils.FXUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ResumeComponent extends BorderPane implements Initializable {

	// model

	private ObjectProperty<Resume> resume = new SimpleObjectProperty<>();
	
	// components
	
	private MapComponent configComponent;
	private MapComponent resultsComponent;

	// view
	
    @FXML
    private ScrollPane configPane;

    @FXML
    private ScrollPane resultsPane;

    @FXML
    private BorderPane bottonPane;

    @FXML
    private TableView<Case> casesTable;

    @FXML
    private TableColumn<Case, String> idColumn;

    @FXML
    private TableColumn<Case, String> membersColumn;

    @FXML
    private TableColumn<Case, Number> gradeColumn;

    @FXML
    private TableColumn<Case, Boolean> skipColumn;

    @FXML
    private TableColumn<Case, String> moodleIdColumn;

    @FXML
    private TableColumn<Case, String> moodleFedbackColumn;

    @FXML
    private TableColumn<Case, String> connStatusColumn;


	public ResumeComponent() {
		FXUtils.load("/fxml/Resume.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize view
		
		configComponent = new MapComponent();
		configPane.setContent(configComponent);

		resultsComponent = new MapComponent();
		resultsPane.setContent(resultsComponent);
		
		// listeners
		
		resume.addListener((o, ov, nv) -> onResumeChanged(o, ov, nv));
		
	}

	// listeners
	
	private void onResumeChanged(ObservableValue<? extends Resume> o, Resume ov, Resume nv) {
		if (nv == null) {
			configComponent.mapProperty().unbind();
			resultsComponent.mapProperty().unbind();
		} else {
			configComponent.mapProperty().bind(nv.configProperty());
			resultsComponent.mapProperty().bind(nv.resultsProperty());
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
