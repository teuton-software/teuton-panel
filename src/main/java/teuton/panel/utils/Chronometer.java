package teuton.panel.utils;

public class Chronometer {
	
	private long init, stop;
	
	public Chronometer() {
		init();
	}
	
	public void init() {
		init = System.currentTimeMillis();
		stop = -1;
	}

	public long stop() {
		stop = System.currentTimeMillis();
		return getDiff();
	}

	public long getDiff() {
		if (stop == -1) return System.currentTimeMillis() - init;
		return stop - init;
	}
	
	@Override
	public String toString() {
		return "(" + init + "-" + stop + ")";
	}

}
