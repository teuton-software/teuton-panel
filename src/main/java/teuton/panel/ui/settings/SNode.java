package teuton.panel.ui.settings;

import teuton.panel.cli.CommandTask;
import teuton.panel.ui.utils.Dialogs;

public class SNode {
	
	public static boolean isInstalled() {
		System.out.println("checking if is a s-node");
		return CommandFactory.getCommand("snode.test").execute().getExitValue() == 0;
	}
	
	public static boolean install() {
		System.out.println("installing s-node");
		CommandTask task = new CommandTask("Installing S-Node...", CommandFactory.getCommand("snode.install"));
		task.setOnSucceeded(e -> {
			Dialogs.info("S-Node installed", "S-Node successfully installed");
		});
		task.setOnFailed(e -> {
			Dialogs.exception("S-Node not installed", "It was not possible to install S-Node.", e.getSource().getException());
		});
		task.setOnCancelled(e -> {
			Dialogs.error("S-Node not installed", "S-Node installation cancelled.");
		});
		return Dialogs.runCommand(task);
	}
	
	public static boolean uninstall() {
		System.out.println("uninstalling s-node");
		CommandTask task = new CommandTask("Uninstalling S-Node...", CommandFactory.getCommand("snode.uninstall"));
		task.setOnSucceeded(e -> {
			Dialogs.info("S-Node uninstalled", "S-Node successfully uninstalled");
		});
		task.setOnFailed(e -> {
			Dialogs.exception("S-Node not uninstalled", "It was not possible to uninstall S-Node.", e.getSource().getException());
		});
		task.setOnCancelled(e -> {
			Dialogs.error("S-Node not uninstalled", "S-Node uninstallation cancelled.");
		});
		return Dialogs.runCommand(task);
	}
	
}
