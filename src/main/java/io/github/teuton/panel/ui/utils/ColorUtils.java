package io.github.teuton.panel.ui.utils;

import javafx.scene.paint.Color;

public class ColorUtils {
	
	public static Color interpolate(double value, double max, Color ... colors) {
		if (value < 0) return colors[0];
		if (value >= max) return colors[colors.length - 1];
		int numColors = colors.length;
		double size = max / (numColors - 1);
		int fragment = (int) (value / size);
		Color firstColor = colors[fragment];
		Color secondColor = colors[fragment + 1];
		value = (value - fragment * size) / size;
		return firstColor.interpolate(secondColor, value);
	}

}
