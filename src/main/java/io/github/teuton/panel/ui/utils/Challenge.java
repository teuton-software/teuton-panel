package io.github.teuton.panel.ui.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Challenge {

	private StringProperty title = new SimpleStringProperty();
	private StringProperty challengeFolder = new SimpleStringProperty();
	private StringProperty configFile = new SimpleStringProperty();

	public final StringProperty titleProperty() {
		return this.title;
	}

	public final String getTitle() {
		return this.titleProperty().get();
	}

	public final void setTitle(final String title) {
		this.titleProperty().set(title);
	}

	public final StringProperty challengeFolderProperty() {
		return this.challengeFolder;
	}

	public final String getChallengeFolder() {
		return this.challengeFolderProperty().get();
	}

	public final void setChallengeFolder(final String challengeFolder) {
		this.challengeFolderProperty().set(challengeFolder);
	}

	public final StringProperty configFileProperty() {
		return this.configFile;
	}

	public final String getConfigFile() {
		return this.configFileProperty().get();
	}

	public final void setConfigFile(final String configFile) {
		this.configFileProperty().set(configFile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Challenge)) {
			return false;
		}
		Challenge other = (Challenge) obj;
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return getTitle();
	}
	
}
