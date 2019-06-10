package teuton.panel.ui.model;

import com.google.gson.annotations.SerializedName;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Config {

	@SerializedName("title")
	private StringProperty title = new SimpleStringProperty();

	@SerializedName("tt_members")
	private StringProperty ttMembers = new SimpleStringProperty();

	@SerializedName("tt_skip")
	private BooleanProperty ttSkip = new SimpleBooleanProperty();

	public final StringProperty titleProperty() {
		return this.title;
	}

	public final String getTitle() {
		return this.titleProperty().get();
	}

	public final void setTitle(final String title) {
		this.titleProperty().set(title);
	}

	public final StringProperty ttMembersProperty() {
		return this.ttMembers;
	}

	public final String getTtMembers() {
		return this.ttMembersProperty().get();
	}

	public final void setTtMembers(final String ttMembers) {
		this.ttMembersProperty().set(ttMembers);
	}

	public final BooleanProperty ttSkipProperty() {
		return this.ttSkip;
	}

	public final boolean isTtSkip() {
		return this.ttSkipProperty().get();
	}

	public final void setTtSkip(final boolean ttSkip) {
		this.ttSkipProperty().set(ttSkip);
	}

}
