package io.github.teuton.panel.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import io.github.teuton.panel.ui.model.Group;
import io.github.teuton.panel.ui.model.Target;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Pair;

public class GroupComponent extends TitledPane implements Initializable {
	
	// model

	private ObjectProperty<Group> group = new SimpleObjectProperty<Group>();
	
	private BooleanProperty completed = new SimpleBooleanProperty();
	
	// view
    
    @FXML
    private Label groupLabel;
    
//    @FXML
//    private TreeTableView<Object> targetsTreeTable;
//    
//    @FXML
//    private TreeTableColumn<Object, String> targetColumn, nameColumn, valueColumn;
    
    @FXML
    private FontIcon rightIcon, failIcon;

	public GroupComponent() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Group.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		group.addListener((o, ov, nv) -> onGroupChanged(o, ov, nv));
						
		rightIcon.managedProperty().bind(completed);
		rightIcon.visibleProperty().bind(rightIcon.managedProperty());
		
		failIcon.managedProperty().bind(completed.not());
		failIcon.visibleProperty().bind(failIcon.managedProperty());
		
//		targetColumn.setCellValueFactory(v -> {
//			if (v.getValue().getValue() instanceof Target) {
//				return new SimpleStringProperty("" + v.getValue().getValue());
//			}
//			return new SimpleStringProperty();
//		});
//		
//		nameColumn.setCellValueFactory(v -> {
//			if (v.getValue().getValue() instanceof Pair) {
//				Pair<String,String> p = (Pair<String,String>) v.getValue().getValue();
//				return new SimpleStringProperty("" + p.getKey());
//			}
//			return new SimpleStringProperty();
//		});
//		
//		valueColumn.setCellValueFactory(v -> {
//			if (v.getValue().getValue() instanceof Pair) {
//				Pair<String,String> p = (Pair<String,String>) v.getValue().getValue();
//				return new SimpleStringProperty("" + p.getValue());
//			}
//			return new SimpleStringProperty();
//		});
				
	}
	
	// listeners

	private void onGroupChanged(ObservableValue<? extends Group> o, Group ov, Group nv) {
		if (ov != null) {
			groupLabel.textProperty().unbind();
//			targetsTreeTable.setRoot(null);
		}
		if (nv != null) {
			groupLabel.textProperty().bind(nv.titleProperty());
//			targetsTreeTable.setRoot(createNodes(nv.getTargets()));
//		    idLabel.textProperty().bind(nv.targetIdProperty());
//		    scoreLabel.textProperty().bind(nv.scoreProperty().asString("%.2f"));
//		    weightLabel.textProperty().bind(nv.weightProperty().asString("%.2f"));
//		    commandLabel.textProperty().bind(nv.commandProperty());
//		    durationLabel.textProperty().bind(nv.durationProperty().asString("%.5f"));
//		    alterationsLabel.textProperty().bind(nv.alterationsProperty());
//		    expectedLabel.textProperty().bind(nv.expectedProperty());
//		    resultLabel.textProperty().bind(nv.resultProperty());
//		    completed.bind(nv.scoreProperty().isEqualTo(nv.weightProperty()));
		}
	}
	
	private TreeItem<Object> createNodes(List<Target> targets) {
		TreeItem<Object> root = new TreeItem<Object>("Root");
		for (Target t: targets) {
			TreeItem<Object> ti = new TreeItem<Object>(t);
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("ID", "" + t.getTargetId())));
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("Alterations", t.getAlterations())));
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("Command", t.getCommand())));
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("Duration", t.getDuration() + "s")));
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("Expected", t.getExpected())));
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("Result", t.getResult())));
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("Score", "" + t.getScore())));
			ti.getChildren().add(new TreeItem<Object>(new Pair<String, String>("Weight", "" + t.getWeight())));
			root.getChildren().add(ti);
		}
		return root;
	}

	// properties
	
	public final ObjectProperty<Group> groupProperty() {
		return this.group;
	}

	public final Group getGroup() {
		return this.groupProperty().get();
	}

	public final void setGroup(final Group group) {
		this.groupProperty().set(group);
	}

}
