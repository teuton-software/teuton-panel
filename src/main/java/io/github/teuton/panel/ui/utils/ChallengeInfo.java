package io.github.teuton.panel.ui.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChallengeInfo {

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

}
