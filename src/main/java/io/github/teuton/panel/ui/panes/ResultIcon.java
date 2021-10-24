package io.github.teuton.panel.ui.panes;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;

public class ResultIcon extends FontIcon {

	private BooleanProperty ok = new SimpleBooleanProperty(false);

	public ResultIcon() {
		super();
		setIconSize(20);
		iconColorProperty().bind(
				Bindings
					.when(ok)
					.then(Color.GREEN)
					.otherwise(Color.RED)
				);
		iconCodeProperty().bind(
				Bindings
					.when(ok)
					.then(FontAwesomeSolid.CHECK_SQUARE)
					.otherwise(FontAwesomeSolid.TIMES_CIRCLE)
				);
	}

	public final BooleanProperty okProperty() {
		return this.ok;
	}

	public final boolean isOk() {
		return this.okProperty().get();
	}

	public final void setOk(final boolean ok) {
		this.okProperty().set(ok);
	}

}
