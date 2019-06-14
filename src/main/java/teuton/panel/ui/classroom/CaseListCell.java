package teuton.panel.ui.classroom;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXListCell;

import teuton.panel.ui.model.Case;

public class CaseListCell extends JFXListCell<Case> {

	private FontIcon icon;

	public CaseListCell() {
		super();
		icon = new FontIcon();
		icon.setIconSize(24);
	}

	@Override
	protected void updateItem(Case c, boolean empty) {
		super.updateItem(c, empty);

		if (empty) {
			
			setText(null);
			setGraphic(null);
			
		} else {

			if (c.getResults().getGrade() >= 100.0)
				icon.setIconCode(FontAwesomeSolid.CHECK_SQUARE);
			else
				icon.setIconCode(FontAwesomeSolid.SQUARE);

			setText("Case " + c.getResults().getCaseId());
			setGraphic(icon);

		}
	}

}
