package teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import teuton.panel.ui.model.Case;

public class CaseComponent extends TabPane implements Initializable {

	private ObjectProperty<Case> _case = new SimpleObjectProperty<>();

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

		caseProperty().addListener((o, ov, nv) -> onCaseChanged(o, ov, nv));
		
	}

	private void onCaseChanged(ObservableValue<? extends Case> o, Case ov, Case nv) {
		if (ov != null) {
			// TODO unbind
		}
		if (nv != null) {
			// TODO bind
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
