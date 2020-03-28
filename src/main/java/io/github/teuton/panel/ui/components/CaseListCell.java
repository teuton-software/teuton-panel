package io.github.teuton.panel.ui.components;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXListCell;

import io.github.teuton.panel.ui.model.cases.Case;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CaseListCell extends JFXListCell<Case> {

	private FontIcon icon;

	public CaseListCell() {
		super();
		icon = new FontIcon();
		icon.setIconSize(24);
		icon.setIconCode(FontAwesomeSolid.USER_CIRCLE);
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
        	MultipleSelectionModel<Case> selectionModel = this.getListView().getSelectionModel();
        	this.getListView().requestFocus();
            if (!this.isEmpty()) {
                int index = this.getIndex();
                if (selectionModel.getSelectedIndices().contains(index)) {
                    selectionModel.clearSelection(index);
                } else {
                    selectionModel.select(index);
                }
                event.consume();
            }
        });
	}

	@Override
	protected void updateItem(Case c, boolean empty) {
		super.updateItem(c, empty);

		if (empty) {
			
			setText(null);
			setGraphic(null);
			
		} else {
			
			double grade = Double.parseDouble(c.getResults().get("grade").toString());
			Color gradeColor;
			if (grade < 50) {
				gradeColor = Color.RED.interpolate(Color.YELLOW, grade / 50.0);
			} else {
				gradeColor = Color.YELLOW.interpolate(Color.FORESTGREEN, (grade - 50.0) / 50.0);
			}
			icon.setIconColor(gradeColor);

			setText("Case " + ((Double)c.getResults().get("case_id")).intValue());
			setGraphic(icon);

		}
	}

}
