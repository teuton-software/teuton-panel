package io.github.teuton.panel.ui.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Settings {

	private StringProperty appVersion; 
	private StringProperty teutonVersion;
	private StringProperty username;
	private StringProperty os;
	private BooleanProperty tNode;
	private BooleanProperty sNode;

	public Settings() {
		appVersion = new SimpleStringProperty(this, "appVersion");
		teutonVersion = new SimpleStringProperty(this, "teutonVersion");
		username = new SimpleStringProperty(this, "username");
		os = new SimpleStringProperty(this, "os");
		tNode = new SimpleBooleanProperty(this, "tNode");
		sNode = new SimpleBooleanProperty(this, "sNode");
	}

	public final StringProperty osProperty() {
		return this.os;
	}

	public final String getOs() {
		return this.osProperty().get();
	}

	public final void setOs(final String os) {
		this.osProperty().set(os);
	}

	public final BooleanProperty tNodeProperty() {
		return this.tNode;
	}

	public final boolean isTNode() {
		return this.tNodeProperty().get();
	}

	public final void setTNode(final boolean tNode) {
		this.tNodeProperty().set(tNode);
	}

	public final BooleanProperty sNodeProperty() {
		return this.sNode;
	}

	public final boolean isSNode() {
		return this.sNodeProperty().get();
	}

	public final void setSNode(final boolean sNode) {
		this.sNodeProperty().set(sNode);
	}

	public final StringProperty teutonVersionProperty() {
		return this.teutonVersion;
	}

	public final String getTeutonVersion() {
		return this.teutonVersionProperty().get();
	}

	public final void setTeutonVersion(final String teutonVersion) {
		this.teutonVersionProperty().set(teutonVersion);
	}
	
	public final StringProperty appVersionProperty() {
		return this.appVersion;
	}

	public final String getAppVersion() {
		return this.appVersionProperty().get();
	}

	public final void setAppVersion(final String appVersion) {
		this.appVersionProperty().set(appVersion);
	}
		
	public final StringProperty usernameProperty() {
		return this.username;
	}

	public final String getUsername() {
		return this.usernameProperty().get();
	}

	public final void setUsername(final String username) {
		this.usernameProperty().set(username);
	}
	
}
