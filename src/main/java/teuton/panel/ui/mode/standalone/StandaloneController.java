package teuton.panel.ui.mode.standalone;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import teuton.panel.model.Challenge;
import teuton.panel.model.Goal;
import teuton.panel.ui.TeutonPanelApp;
import teuton.panel.ui.mode.ModeController;
import teuton.panel.ui.utils.Controller;
import teuton.panel.utils.MarkdownUtils;

public class StandaloneController extends Controller<BorderPane> {

	// model

	private ObjectProperty<Class<?>> view = new SimpleObjectProperty<>(this, "view");
	private BooleanProperty loading = new SimpleBooleanProperty(this, "loading");

	private ChallengeTask challengeTask;

	private StringProperty challengeDescription = new SimpleStringProperty(this, "challengeDescription");
	private StringProperty goalDescription = new SimpleStringProperty(this, "goalDescription");

	private BooleanProperty running = new SimpleBooleanProperty(this, "running", false);
	private BooleanProperty paused = new SimpleBooleanProperty(this, "paused", false);

	private ObjectProperty<Goal> selectedGoal = new SimpleObjectProperty<>(this, "selectedGoal");
	private ObjectProperty<Challenge> challenge = new SimpleObjectProperty<>(this, "challenge");

	// view

	@FXML
	private WebView challengeView, goalView;

	@FXML
	private ListView<Goal> goalsList;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab challengeTab, goalsTab;

	@FXML
	private JFXButton startButton, pauseButton, restartButton, openButton;

	public StandaloneController() {
		super("/fxml/Standalone.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		selectedGoal.bind(goalsList.getSelectionModel().selectedItemProperty());
//		selectedGoal.addListener((o, ov, nv) -> onGoalSelectionChanged(ov, nv));
//
//		challenge.addListener((o, ov, nv) -> onChallengeChanged(ov, nv));
//
//		startButton.disableProperty().bind(challenge.isNull().or(running)); 		// deshabilita el bot�n de inicio si no hay reto o est� ejecut�ndose
//		pauseButton.disableProperty().bind(challenge.isNull().or(running.not())); 	// deshabilita el bot�n de pausa si no hay reto o est� parado
//		restartButton.disableProperty().bind(challenge.isNull().or(running)); 			// deshabilita el bot�n de reinicio si no hay reto
//
//		goalsList.setCellFactory((ListView<Goal> lv) -> new GoalListCell()); 		// personaliza las celdas de la lista de objetivos
//
//		challengeDescription.addListener((o, ov, nv) -> onChallengeDescriptionChanged(nv));
//
//		goalDescription.addListener((o, ov, nv) -> onGoalDescriptionChanged(nv));
//		
//		challengeView.getEngine().getLoadWorker().stateProperty().addListener((o, ov, nv) -> {
//			if (nv == State.SUCCEEDED) {
//				loading.set(false);
//			}
//		});

	}

	// event listeners

	/**
	 * Evento que se dispara el cambiar la descripci�n del reto
	 * 
	 * @param ov
	 * @param nv
	 */
	private void onChallengeDescriptionChanged(String nv) {
		if (nv != null) {
			Platform.runLater(() -> challengeView.getEngine().loadContent(MarkdownUtils.render(nv)));
		}
	}

	/**
	 * Evento que se dispara
	 * 
	 * @param nv
	 *            Nueva descripci�n
	 */
	private void onGoalDescriptionChanged(String nv) {
		if (nv != null) {
			Platform.runLater(() -> goalView.getEngine().loadContent(MarkdownUtils.render(nv)));
		} else {
			Platform.runLater(() -> goalView.getEngine().loadContent(MarkdownUtils.render(":warning: No hay ning�n objetivo seleccionado.")));
		}
	}

	/**
	 * Evento que se dispara cuando se selecciona un objetivo de la lista
	 * 
	 * @param ov
	 * @param nv
	 */
	private void onGoalSelectionChanged(Goal ov, Goal nv) {
		if (ov != null) {
			goalDescription.unbind();
		}
		if (nv != null) {
			goalDescription.bind(nv.descriptionProperty());
		}
	}

	/**
	 * Evento que se dispara cuando se establece un nuevo reto
	 * 
	 * @param ov
	 * @param nv
	 */
	private void onChallengeChanged(Challenge ov, Challenge nv) {
		// comprueba si hab�a un reto cargado
		if (ov != null) {
			System.out.println("desbindeando challenge " + ov.getName());

			TeutonPanelApp.getPrimaryStage().setTitle("Challenger");

			challengeDescription.unbind();
			challengeDescription.set("");
			
			goalDescription.unbind();
			goalsList.itemsProperty().unbind();
			if (challengeTask != null && challengeTask.isRunning()) {
				challengeTask.stop();
			}
			challengeTask = null;
		}
		// comprueba si hay un reto nuevo
		if (nv != null) {
			System.out.println("bindeando challenge " + nv.getName());

			TeutonPanelApp.getPrimaryStage().setTitle("Challenger :: " + nv.getName());

			tabPane.getSelectionModel().select(challengeTab);

			challengeDescription.bind(nv.descriptionProperty());
			goalsList.itemsProperty().bind(nv.goalsProperty());
			goalsList.getSelectionModel().selectFirst();

			// poner listener a los achieved de todos los goals, para mostrar notificaci�n y
			// enfocar el siguiente objetivo
			for (int i = 0; i < nv.getGoals().size(); i++) {
				Goal currentGoal = nv.getGoals().get(i);
				final Goal nextGoal = (i < nv.getGoals().size() - 1) ? nv.getGoals().get(i + 1) : null;
				currentGoal.achievedProperty().addListener((ob, oldv, newv) -> {
					if (newv) {
						onGoalAchieved(currentGoal, nextGoal);
					}
				});
			}

		}
	}

	/**
	 * Evento que se dispara cuando un objetivo se marca como conseguido (achieved)
	 * 
	 * @param currentGoal
	 * @param nextGoal
	 */
	private void onGoalAchieved(Goal currentGoal, Goal nextGoal) {
		String message;
		if (nextGoal != null) {
			goalsList.getSelectionModel().select(nextGoal);
			message = "�Buen trabajo! Has cumplido el objetivo '" + currentGoal.getName()
					+ "'.\nVe al Challenger a consultar tu nuevo objetivo.";
		} else {
			goalsList.getSelectionModel().clearSelection();
			message = "�Enhorabuena! Has cumplido el �ltimo objetivo y has completado el reto";
		}
		Platform.runLater(() -> {
			Notifications.create().title("Objetivo completado").text(message).owner(TeutonPanelApp.getPrimaryStage()).showInformation();
			tabPane.getSelectionModel().select(goalsTab);
			goalsList.requestFocus();
		});
	}

	/**
	 * Evento que se dispara al pulsar el bot�n de iniciar (play)
	 * 
	 * @param e
	 */
	@FXML
	private void onStartAction(ActionEvent e) {

		// si se "paus�" el reto, no se resetea antes de volver a iniciarlo
		if (paused.get()) {
			System.out.println("Reanudando reto");
		} else {
			System.out.println("Iniciando reto");
		}

		// cambia a la pesta�a de objetivos
		tabPane.getSelectionModel().select(goalsTab);

		// se crea una tarea para ejecutar el reto en segundo plano
		challengeTask = new ChallengeTask(challenge.get());

		// se pone un listener al estado de la tarea para cambiar la propiedad "running"
		// y controlar el estado de los botones
		challengeTask.stateProperty().addListener((o, ov, nv) -> {
			running.set(nv == State.RUNNING);
		});

		challengeTask.setOnSucceeded(v -> {
			Boolean result = (Boolean) v.getSource().getValue();

			if (result && challenge.get() != null) {

				Alert mensaje = new Alert(AlertType.INFORMATION);
				mensaje.initOwner(TeutonPanelApp.getPrimaryStage());
				mensaje.setTitle(TeutonPanelApp.getPrimaryStage().getTitle());
				mensaje.setHeaderText("�Reto completado!");
				mensaje.setContentText(String.format("Enhorabuena, has completado el reto '%s'.", challenge.get().getName()));
				mensaje.showAndWait();

			}

		});

		challengeTask.setOnScheduled(v -> {


		});

		// se deshabilita la pausa
		paused.set(false);

		// se lanza la tarea
		Thread thread = new Thread(challengeTask);
		thread.setDaemon(true); // como demonio, para que finalice la aplicaci�n si �ste el �nico hilo que queda vivo
		thread.start();
	}

	/**
	 * Evento que se dispara al pulsar el bot�n de pausa (pause)
	 * 
	 * @param e
	 */
	@FXML
	private void onPauseAction(ActionEvent e) {
		System.out.println("Pausando reto");
		paused.set(true);
		challengeTask.stop();
	}

	/**
	 * Evento que se dispara al pulsar el bot�n de reiniciar (restart)
	 * 
	 * @param e
	 */
	@FXML
	private void onRestartAction(ActionEvent e) {
		System.out.println("Reiniciando reto");
	}

	/**
	 * Evento que se dispara al pulsar el bot�n de cerrar (close)
	 * 
	 * @param e
	 */
	@FXML
	private void onCloseAction(ActionEvent e) {
		System.out.println("Cerrando reto");
		challenge.set(null);
		view.set(ModeController.class);
	}

	// properties

	public final ObjectProperty<Class<?>> viewProperty() {
		return this.view;
	}

	public final Class<?> getView() {
		return this.viewProperty().get();
	}

	public final void setView(final Class<?> view) {
		this.viewProperty().set(view);
	}

	public final BooleanProperty loadingProperty() {
		return this.loading;
	}

	public final boolean isLoading() {
		return this.loadingProperty().get();
	}

	public final void setLoading(final boolean loading) {
		this.loadingProperty().set(loading);
	}

}
