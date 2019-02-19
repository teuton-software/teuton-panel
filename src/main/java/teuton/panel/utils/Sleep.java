package teuton.panel.utils;

/**
 * Sleep current thread for the specified millis, seconds or minutes.
 * @author fvarrui
 */
public class Sleep {

	public static void millis(long ms) {
		if (ms <= 0) return;
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	public static void seconds(int seconds) {
		millis(seconds * 1000);
	}

	public static void minutes(int minutes) {
		seconds(minutes * 60);
	}

}
