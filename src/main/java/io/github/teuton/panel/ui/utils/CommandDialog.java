package io.github.teuton.panel.ui.utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import io.github.teuton.panel.cli.CommandTask;
import io.github.teuton.panel.utils.MessageConsumer;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class CommandDialog extends Dialog<Void>  implements Initializable {

	private CommandTask task;
		
	@FXML
	private Label messageLabel;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private TextArea outputText; 
	
	@FXML
	private GridPane root;
	
    public CommandDialog(CommandTask task) {
    	super();
    	this.task = task;
    	try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CommandDialog.fxml"));
	    	loader.setController(this);
	    	loader.load();
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	task.setConsumer(new MessageConsumer(outputText));

    	setResizable(true);
    	setGraphic(new FontIcon("fas-cogs:48:darkgray"));
    		
    	getDialogPane().getButtonTypes().setAll(ButtonType.CANCEL);
    	getDialogPane().setContent(root);
    	getDialogPane().getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
    	
    	headerTextProperty().bind(task.titleProperty());
    	messageLabel.textProperty().bind(task.messageProperty());
    	progressBar.progressProperty().bind(task.progressProperty());
    
    	setOnCloseRequest(e -> {
    		if (task.isRunning()) task.cancel();
    	});
    	
    	task.setOnScheduled(e -> task.setConsumer(new MessageConsumer(outputText)));
    	task.stateProperty().addListener((o, ov, nv) -> {
    		if (nv.equals(State.SUCCEEDED) || nv.equals(State.FAILED))
    			((Button)getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Close");
    	});
    	
    	showingProperty().addListener((o, ov, nv) -> {
    		if (nv) new Thread(task).start();
    	});
    	
    }

}
