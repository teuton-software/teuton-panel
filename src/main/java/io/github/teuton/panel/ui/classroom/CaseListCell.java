package io.github.teuton.panel.ui.classroom;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXListCell;

import io.github.teuton.panel.ui.model.Case;
import javafx.scene.paint.Color;

public class CaseListCell extends JFXListCell<Case> {

	private FontIcon icon;

	public CaseListCell() {
		super();
		icon = new FontIcon();
		icon.setIconSize(24);
		icon.setIconCode(FontAwesomeSolid.USER_CIRCLE);
	}

	@Override
	protected void updateItem(Case c, boolean empty) {
		super.updateItem(c, empty);

		if (empty) {
			
			setText(null);
			setGraphic(null);
			
		} else {
			
			int grade = c.getResults().getGrade();
			Color gradeColor;
			if (grade < 50) {
				gradeColor = Color.RED.interpolate(Color.YELLOW, grade / 50.0);
			} else {
				gradeColor = Color.YELLOW.interpolate(Color.FORESTGREEN, (grade - 50.0) / 50.0);
			}
			icon.setIconColor(gradeColor);

			setText("Case " + c.getResults().getCaseId());
			setGraphic(icon);

		}
	}

}
