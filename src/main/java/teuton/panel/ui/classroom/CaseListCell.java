package teuton.panel.ui.classroom;

import com.jfoenix.controls.JFXListCell;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import teuton.panel.ui.model.Case;

public class CaseListCell extends JFXListCell<Case> {

	private FontAwesomeIconView icon;

	public CaseListCell() {
		super();
		icon = new FontAwesomeIconView(FontAwesomeIcon.SQUARE_ALT);
		icon.setGlyphSize(24);
	}

	@Override
	protected void updateItem(Case c, boolean empty) {
		super.updateItem(c, empty);

		if (empty) {
			
			setText(null);
			setGraphic(null);
			
		} else {

			if (c.getResults().getGrade() >= 100.0)
				icon.setIcon(FontAwesomeIcon.CHECK_SQUARE_ALT);
			else
				icon.setIcon(FontAwesomeIcon.SQUARE_ALT);

			setText("Case " + c.getResults().getCaseId());
			setGraphic(icon);

		}
	}

}
