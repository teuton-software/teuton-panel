package io.github.teuton.panel.ui.utils;

import java.io.File;
import java.io.IOException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import io.github.teuton.panel.utils.JSONUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.stage.Stage;

public class Config {

	public static final File configDir = new File(System.getProperty("user.home"), ".teuton-panel");
	public static final File submissionsDir = new File(configDir, "submissions");
	public static final File configFile = new File(configDir, "config.json");

	private static Config config;

	public static Config getConfig() {
		return config;
	}

	public static void load() {
		if (!configDir.exists()) {
			configDir.mkdir();
		}
		if (configFile.exists()) {
			try {
				config = JSONUtils.loadFromJson(configFile, Config.class);
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				Dialogs.exception("No se pudo cargar la configuración desde el fichero '" + configFile + "'.",
						e.getMessage(), e);
			}
		} else {
			config = new Config();
		}
	}

	public static void save() {
		try {
			JSONUtils.jsonToFile(config, configFile);
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			Dialogs.exception("No se pudo guardar la configuración en el fichero '" + configFile + "'.", e.getMessage(),
					e);
		}
	}

	public void storeStage(Stage stage) {
		setStageCoords(new Point2D(stage.getX(), stage.getY()));
		setStageSize(new Dimension2D(stage.getWidth(), stage.getHeight()));
		setMaximized(stage.isMaximized());
	}

	public void restoreStage(Stage stage) {
		if (Config.getConfig().getStageCoords() != null) {
			stage.setX(getStageCoords().getX());
			stage.setY(getStageCoords().getY());
		}
		if (Config.getConfig().getStageSize() != null) {
			stage.setWidth(getStageSize().getWidth());
			stage.setHeight(getStageSize().getHeight());
		}
		stage.setMaximized(isMaximized());
	}

	// =================================
	// config properties
	// =================================

	private ObjectProperty<Dimension2D> stageSize = new SimpleObjectProperty<>();
	private ObjectProperty<Point2D> stageCoords = new SimpleObjectProperty<>();
	private BooleanProperty maximized = new SimpleBooleanProperty();
	private ListProperty<ChallengeInfo> recentChallenges = new SimpleListProperty<>(
			FXCollections.observableArrayList());

	public final ObjectProperty<Dimension2D> stageSizeProperty() {
		return this.stageSize;
	}

	public final Dimension2D getStageSize() {
		return this.stageSizeProperty().get();
	}

	public final void setStageSize(final Dimension2D stageSize) {
		this.stageSizeProperty().set(stageSize);
	}

	public final ObjectProperty<Point2D> stageCoordsProperty() {
		return this.stageCoords;
	}

	public final Point2D getStageCoords() {
		return this.stageCoordsProperty().get();
	}

	public final void setStageCoords(final Point2D stageCoords) {
		this.stageCoordsProperty().set(stageCoords);
	}

	public final BooleanProperty maximizedProperty() {
		return this.maximized;
	}

	public final boolean isMaximized() {
		return this.maximizedProperty().get();
	}

	public final void setMaximized(final boolean maximized) {
		this.maximizedProperty().set(maximized);
	}

	public final ListProperty<ChallengeInfo> recentChallengesProperty() {
		return this.recentChallenges;
	}

	public final ObservableList<ChallengeInfo> getRecentChallenges() {
		return this.recentChallengesProperty().get();
	}

	public final void setRecentChallenges(final ObservableList<ChallengeInfo> recentChallenges) {
		this.recentChallengesProperty().set(recentChallenges);
	}

}
