package io.github.teuton.panel.utils;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import io.github.teuton.panel.ui.utils.Dialogs;

public class DesktopUtils {
	
	public static void browse(String url) {
		if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (Exception e1) {
				e1.printStackTrace();
				Dialogs.exception("Error opening browser", e1.getMessage(), e1);
			}
		}
	}
	
	public static void open(File file) {
		if (Desktop.getDesktop().isSupported(Desktop.Action.APP_OPEN_FILE)) {
		    try {
				Desktop.getDesktop().open(file);
			} catch (Exception e1) {
				e1.printStackTrace();
				Dialogs.exception("Error opening file", e1.getMessage(), e1);
			}
		}
	}

}
