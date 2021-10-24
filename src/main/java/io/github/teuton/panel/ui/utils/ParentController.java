package io.github.teuton.panel.ui.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public abstract class ParentController extends Controller<StackPane> {
	
	public Map<Class<? extends Controller<?>>, Controller<?>> controllers;
	
	public ParentController() {
		super(new StackPane());
		controllers = new HashMap<>();
		shownProperty().addListener((o, ov, nv) -> show(nv));
		initialize(null, null);
	}
	
	@SuppressWarnings("unchecked")
	public void registerController(Controller<?> controller) {
		controllers.put((Class<? extends Controller<?>>) controller.getClass(), controller);
		controller.shownProperty().bindBidirectional(shownProperty());
	}
	
	public final Map<Class<? extends Controller<?>>, Controller<?>> getControllers() {
		return controllers;
	}
	
	public Controller<?> getController(Class<? extends Controller<?>> c) {
		return controllers.get(c);
	}
	
	public void show(Class<? extends Controller<?>> c) {
		show(getController(c).getRoot());
	}
	
	protected abstract void show(Parent node);

}
