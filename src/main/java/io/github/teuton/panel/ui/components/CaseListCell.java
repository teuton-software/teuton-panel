package io.github.teuton.panel.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;

import io.github.teuton.panel.ui.model.cases.Case;
import io.github.teuton.panel.ui.utils.ColorUtils;
import io.github.teuton.panel.ui.utils.FXUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CaseListCell extends JFXListCell<Case> implements Initializable {

	// model

	private Case _case;
	private BooleanProperty checked = new SimpleBooleanProperty();

	// view

	@FXML
	private GridPane root;

	@FXML
	private FontIcon icon;
	
	@FXML
	private Label caseLabel, membersLabel, gradeLabel;

	@FXML
	private JFXCheckBox selectedCheck;

	public CaseListCell() {
		super();
		FXUtils.loadView("/fxml/CaseListCell.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		
		checked.bindBidirectional(selectedCheck.selectedProperty());
		
		listViewProperty().addListener((o, ov, nv) -> prefWidthProperty().bind(nv.widthProperty()));

	}

	@Override
	protected void updateItem(Case c, boolean empty) {
		super.updateItem(c, empty);

		if (empty) {

			setGraphic(null);
			if (_case != null) checked.unbindBidirectional(_case.selectedProperty());
 
		} else {
			
			checked.bindBidirectional(c.selectedProperty());
			_case = c;
			
			double grade = (double) c.getResults().get("grade");

			icon.setIconColor(ColorUtils.interpolate(grade, 100, Color.RED, Color.YELLOW, Color.GREEN));

			caseLabel.setText("Case " + c.getResults().get("case_id"));
			membersLabel.setText(c.getConfig().get("tt_members").toString());
			gradeLabel.setText(String.format("%.0f", grade));

			setGraphic(root);

		}
	}

	public final BooleanProperty checkedProperty() {
		return this.checked;
	}

	public final boolean isChecked() {
		return this.checkedProperty().get();
	}

	public final void setChecked(final boolean checked) {
		this.checkedProperty().set(checked);
	}

}
