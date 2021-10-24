package io.github.teuton.panel.ui.panes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.teuton.panel.ui.model.cases.Group;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class GroupsPane extends SplitPane implements Initializable {

	// model

	private ListProperty<Group> groups = new SimpleListProperty<>();

	// components
	
	private ListPane targetComponent;
	
	// view
	
	@FXML
	private ScrollPane targetPane;

	@FXML
	private TreeTableView<Object> targetsTreeTable;

	@FXML
	private TreeTableColumn<Object, String> descriptionColumn;

	@FXML
	private TreeTableColumn<Object, String> scoreColumn;

	public GroupsPane() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Groups.fxml"));
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
		
		targetComponent = new ListPane("case.target.order");

		targetPane.setContent(targetComponent);
		
		groups.addListener((o, ov, nv) -> onGroupsChanged(o, ov, nv));

		targetsTreeTable.setShowRoot(false);
		
		targetsTreeTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> onTargetSelectedChanged(o, ov, nv));

		descriptionColumn.setCellValueFactory(v -> {
			if (v.getValue().getValue() instanceof Group) {

				Group group = (Group) v.getValue().getValue();
				return group.titleProperty();

			} else if (v.getValue().getValue() instanceof MapProperty) {

				MapProperty<String,Object> target = (MapProperty<String,Object>) v.getValue().getValue();
				return new SimpleStringProperty(target.get("description").toString());

			}
			return new SimpleStringProperty("");
		});

		scoreColumn.setCellValueFactory(v -> {
			if (v.getValue().getValue() instanceof MapProperty) {
				MapProperty<String,Object> target = (MapProperty<String,Object>) v.getValue().getValue();
				return new SimpleStringProperty(target.get("score").toString());
			}
			return new SimpleStringProperty("");
		});
		
	}

	@SuppressWarnings("unchecked")
	private void onTargetSelectedChanged(ObservableValue<? extends TreeItem<Object>> o, TreeItem<Object> ov, TreeItem<Object> nv) {
		
		if (ov != null) {
			
			targetComponent.setItems(null);
			
		}

		if (nv != null) {
			
			if (nv.getValue() instanceof MapProperty) {
				
				MapProperty<String,Object> target = (MapProperty<String,Object>) nv.getValue();
				targetComponent.setItems(target);
				
			}
			
		}
		
	}

	private void onGroupsChanged(ObservableValue<? extends ObservableList<Group>> o, ObservableList<Group> ov, ObservableList<Group> nv) {

		if (ov != null) {
			targetsTreeTable.setRoot(null);
		}
		
		if (nv != null) {

			TreeItem<Object> root = new TreeItem<>();

			for (Group group : nv) {
				TreeItem<Object> groupItem = new TreeItem<Object>(group);
				groupItem.setExpanded(true);

				for (MapProperty<String, Object> target : group.getTargets()) {
					TreeItem<Object> targetItem = new TreeItem<Object>(target);
					groupItem.getChildren().add(targetItem);
				}

				root.getChildren().add(groupItem);
			}

			targetsTreeTable.setRoot(root);

		}
	}

	public final ListProperty<Group> groupsProperty() {
		return this.groups;
	}

	public final ObservableList<Group> getGroups() {
		return this.groupsProperty().get();
	}

	public final void setGroups(final ObservableList<Group> groups) {
		this.groupsProperty().set(groups);
	}

}
