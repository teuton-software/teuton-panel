package io.github.teuton.panel.cli;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.SystemUtils;

public class CommandFactory {

	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("cli.commands", new Locale(getOS().name()));
	
	private static Shell SHELL = new Shell(
			BUNDLE.getString("shell.name"),
			BUNDLE.getString("shell.executable"),
			BUNDLE.getString("shell.arguments").split(",")
		);
	
	public static OS getOS() {
		if (SystemUtils.IS_OS_WINDOWS) return OS.WINDOWS;
		if (SystemUtils.IS_OS_LINUX) return OS.LINUX;
		if (SystemUtils.IS_OS_MAC) return OS.MACOSX;
		return null;
	}
	
	public static Command getCommand(String name) {
		return new Command(SHELL, BUNDLE.getString(name));
	}
	
}
