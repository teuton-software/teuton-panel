package io.github.teuton.panel.ui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Settings {

	private StringProperty appVersion = new SimpleStringProperty(this, "appVersion"); 
	private StringProperty teutonVersion = new SimpleStringProperty(this, "teutonVersion");
	private StringProperty username = new SimpleStringProperty(this, "username");
	private StringProperty os = new SimpleStringProperty(this, "os");

	public final StringProperty osProperty() {
		return this.os;
	}

	public final String getOs() {
		return this.osProperty().get();
	}

	public final void setOs(final String os) {
		this.osProperty().set(os);
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
