package teuton.panel.ui.settings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import teuton.panel.cli.CommandTask;
import teuton.panel.cli.ExecutionResult;
import teuton.panel.ui.utils.Dialogs;

public class TNode {
	
	private static final Pattern VERSION_PATTERN = Pattern.compile("^teuton *\\(version *(.*)\\)$");
	
	public static String getTeutonVersion() {
		System.out.print("getting teuton version: ");
		ExecutionResult result = CommandFactory.getCommand("tnode.version").execute();
		Matcher m = VERSION_PATTERN.matcher(result.getOutput());
		if (m.matches()) {
			System.out.println(m.group(1));
			return m.group(1);
		}
		System.out.println("not installed");
		return null;
	}
	
	public static boolean isInstalled() {
		System.out.println("checking if is a t-node");
		return getTeutonVersion() != null;
	}
	
	public static boolean install() {
		System.out.println("installing t-node");
		CommandTask task = new CommandTask("Installing T-Node...", CommandFactory.getCommand("tnode.install"));
		task.setOnSucceeded(e -> {
			Dialogs.info("T-Node installed", "T-Node successfully installed");
		});
		task.setOnFailed(e -> {
			Dialogs.exception("T-Node not installed", "It was not possible to install T-Node.", e.getSource().getException());
		});
		task.setOnCancelled(e -> {
			Dialogs.error("T-Node not installed", "T-Node installation cancelled.");
		});
		return Dialogs.runCommand(task);
	}
	
	public static boolean uninstall() {
		System.out.println("uninstalling t-node");
		CommandTask task = new CommandTask("Uninstalling T-Node...", CommandFactory.getCommand("tnode.uninstall"));
		task.setOnSucceeded(e -> {
			Dialogs.info("T-Node uninstalled", "T-Node successfully uninstalled");
		});
		task.setOnFailed(e -> {
			Dialogs.exception("T-Node not uninstalled", "It was not possible to uninstall T-Node.", e.getSource().getException());
		});
		task.setOnCancelled(e -> {
			Dialogs.error("T-Node not uninstalled", "T-Node uninstallation cancelled.");
		});
		return Dialogs.runCommand(task);
	}

	public static boolean update() {
		System.out.println("updating t-node");
		CommandTask task = new CommandTask("Updating T-Node", CommandFactory.getCommand("tnode.update"));
		task.setOnSucceeded(e -> {
			Dialogs.info("T-Node updated", "T-Node successfully updated to version " + getTeutonVersion());
		});
		task.setOnFailed(e -> {
			Dialogs.exception("T-Node not updated", "It was not possible to update T-Node.", e.getSource().getException());
		});
		task.setOnCancelled(e -> {
			CommandFactory.getCommand("tnode.cancel");
			Dialogs.error("T-Node not updated", "T-Node updating cancelled.");
		});
		return Dialogs.runCommand(task);
	}

}
