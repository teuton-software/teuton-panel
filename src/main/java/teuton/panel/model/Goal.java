package teuton.panel.model;

import org.apache.commons.lang.StringUtils;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Clase Modelo que representa un objetivo
 * @author Fran Vargas
 */
public class Goal {
	
	private StringProperty name;
	private StringProperty description;

	private ReadOnlyBooleanWrapper achieved;

	/**
	 * Constructor por defecto
	 */
	public Goal() {
		this(null);
	}

	/**
	 * Constructor
	 * @param name nombre del objetivo
	 */
	public Goal(String name) {
		this(name, null);
	}


	/**
	 * 
	 * @param name nombre del objetivo
	 * @param description del objetivo
	 */
	public Goal(String name, String description) {
		super();
		this.name = new SimpleStringProperty(this, "name", name);
		this.description = new SimpleStringProperty(this, "description", description);
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

	public final ReadOnlyBooleanProperty achievedProperty() {
		return this.achieved.getReadOnlyProperty();
	}
	
	public final boolean isAchieved() {
		return this.achievedProperty().get();
	}
	
	public String toString(int spaces) {
		return StringUtils.repeat("-", spaces) + " (" + (achieved.get() ? "+" : "-") + ") [goal] " + getName();
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
