package teuton.panel.ui.settings;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang.SystemUtils;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import teuton.panel.ui.utils.Controller;
import teuton.panel.ui.utils.Dialogs;

public class SettingsController extends Controller<BorderPane> {

	// model

	private ReadOnlyObjectWrapper<Settings> settings;

	// view
	private PopOver popOver;

	@FXML
	private Label usernameLabel, osLabel, appVersionLabel, teutonVersionLabel;

	@FXML
	private JFXToggleButton tNodeToggleButton, sNodeToggleButton;

	@FXML
	private JFXButton upgradeButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// set initial app settings
		Settings settings = new Settings();
		settings.setOs(String.format("%s (%s)", SystemUtils.OS_NAME, SystemUtils.OS_VERSION));
		settings.setTeutonVersion(TNode.getTeutonVersion());
		settings.setTNode(settings.getTeutonVersion() != null);
		settings.setSNode(SNode.isInstalled());
		settings.setAppVersion(ResourceBundle.getBundle("teuton-panel").getString("teuton.panel.version"));
		settings.setUsername(System.getProperty("user.name"));

		this.settings = new ReadOnlyObjectWrapper<>(settings);

		// config popup to show settings
		popOver = new PopOver(getRoot());
		popOver.setTitle("Settings");
		popOver.setArrowLocation(ArrowLocation.TOP_RIGHT);
		popOver.setCloseButtonEnabled(true);
		popOver.setHeaderAlwaysVisible(true);
		popOver.setDetachable(false);
		popOver.setCornerRadius(2);
		popOver.setAutoHide(true);
		popOver.setAutoFix(false);
		popOver.setFadeInDuration(Duration.millis(1));
		popOver.setFadeOutDuration(Duration.millis(1));

		// bind view to model
		tNodeToggleButton.selectedProperty().bindBidirectional(settings.tNodeProperty());
		sNodeToggleButton.selectedProperty().bindBidirectional(settings.sNodeProperty());
		osLabel.textProperty().bind(settings.osProperty());
		appVersionLabel.textProperty().bind(settings.appVersionProperty());
		usernameLabel.textProperty().bind(settings.usernameProperty());
		teutonVersionLabel.textProperty().bind(Bindings.when(settings.teutonVersionProperty().isNotEmpty())
				.then(settings.teutonVersionProperty()).otherwise("Not installed"));
		upgradeButton.disableProperty().bind(settings.tNodeProperty().not());

	}

	@FXML
	private void onTNodeToggleButtonAction(ActionEvent e) {
		System.out.println("onTNodeToggleButtonAction: " + tNodeToggleButton.isSelected());
		if (tNodeToggleButton.isSelected()) {

			if (Dialogs.confirm("Install T-Node", "Do you want to install T-Node?") && TNode.install())
				settings.get().setTeutonVersion(TNode.getTeutonVersion());
			else
				tNodeToggleButton.setSelected(false);

		} else {

			if (Dialogs.confirm("Uninstall T-Node", "Do you want to uninstall T-Node?") && TNode.uninstall())
				settings.get().setTeutonVersion(null);
			else
				tNodeToggleButton.setSelected(true);
		}
	}

	@FXML
	private void onSNodeToggleButtonAction(ActionEvent e) {
		System.out.println("s-node action: " + sNodeToggleButton.isSelected());
		if (sNodeToggleButton.isSelected()) {

			if (!(Dialogs.confirm("Install S-Node", "Do you want to install S-Node?") && SNode.install()))
				sNodeToggleButton.setSelected(false);

		} else {

			if (!(Dialogs.confirm("Uninstall S-Node", "Do you want to uninstall S-Node?") && SNode.uninstall()))
				sNodeToggleButton.setSelected(true);

		}
	}

	@FXML
	private void onUpgradeButtonAction(ActionEvent e) {
		if (Dialogs.confirm("Upgrade T-Node", "Do you want to upgrade Teuton?") && TNode.update()) {
			settings.get().setTeutonVersion(TNode.getTeutonVersion());
		}
	}

	public void showPopOver(Node owner) {
		if (!popOver.isShowing())
			popOver.show(owner);
		else
			popOver.hide();
	}

	public final ReadOnlyObjectProperty<Settings> settingsProperty() {
		return this.settings.getReadOnlyProperty();
	}

	public final Settings getSettings() {
		return this.settingsProperty().get();
	}

}
