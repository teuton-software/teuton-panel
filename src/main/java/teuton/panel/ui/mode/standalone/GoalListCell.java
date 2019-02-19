package teuton.panel.ui.mode.standalone;

import com.jfoenix.controls.JFXListCell;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import teuton.panel.model.Goal;

public class GoalListCell extends JFXListCell<Goal> {
	
	private FontAwesomeIconView icon;

	public GoalListCell() {
		super();
		icon = new FontAwesomeIconView(FontAwesomeIcon.SQUARE_ALT);
//		icon.setGlyphSize(24);
	}
	
	@Override
	protected void updateItem(Goal goal, boolean empty) {
		super.updateItem(goal, empty);
		
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
        	
       		goal.achievedProperty().addListener((o, ov, nv) -> {
//       			if (nv) icon.setIcon(FontAwesomeIcon.CHECK_SQUARE_ALT);
//       			else icon.setIcon(FontAwesomeIcon.SQUARE_ALT);
       		});
        	
            setText(goal.getName());
//            setGraphic(icon);
        }
	}
	
}
