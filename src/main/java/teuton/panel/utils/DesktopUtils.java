package teuton.panel.utils;

import java.awt.Desktop;
import java.net.URI;

import teuton.panel.ui.utils.Dialogs;

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

}
