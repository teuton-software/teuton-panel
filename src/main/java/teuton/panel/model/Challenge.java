package teuton.panel.model;

import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Clase Modelo que representa un Reto
 * @author Fran Vargas
 */
public class Challenge {

	private StringProperty name;
	private StringProperty description;
	private ListProperty<Goal> goals;
	private ReadOnlyBooleanWrapper achieved;

	/**
	 * Constructor por defecto
	 */
	public Challenge() {
		this(null);
	}

	/**
	 * Constructor
	 * @param name nombre del reto
	 */
	public Challenge(String name) {
		this.name = new SimpleStringProperty(this, "name", name);
		this.description = new SimpleStringProperty(this, "description");
		this.goals = new SimpleListProperty<>(this, "goals", FXCollections.observableArrayList());
		this.achieved = new ReadOnlyBooleanWrapper(this, "achieved", false);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public final ListProperty<Goal> goalsProperty() {
		return this.goals;
	}

	public final ObservableList<Goal> getGoals() {
		return this.goalsProperty().get();
	}

	public final void setGoals(final ObservableList<Goal> goals) {
		this.goalsProperty().set(goals);
	}
	
	public final ReadOnlyBooleanProperty achievedProperty() {
		return this.achieved.getReadOnlyProperty();
	}

	public final boolean isAchieved() {
		return this.achievedProperty().get();
	}

	public String toString(int spaces) {
		return StringUtils.repeat("-", spaces) + "--- Comprobando objetivos:\n" + goals.stream().map(g -> g.toString(7)).collect(Collectors.joining("\n"));
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
