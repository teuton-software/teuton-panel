package io.github.teuton.panel.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.function.Consumer;

/**
 * Procesa flujos de entrada en segundo plano.
 * @author fvarrui
 */
public class StreamCharacterConsumer extends Thread {
	
	private static final char EOT = (char) 4; // End Of Transmission character
	
	private volatile boolean stop;
	private InputStream inputStream;
	private Consumer<String> consumer;

	public StreamCharacterConsumer(InputStream inputStream, Consumer<String> consumer) {
		super("StreamCharacterConsumer");
		this.inputStream = inputStream;
		this.consumer = consumer;
		setDaemon(false);
	}

	@Override
	public void run() {
		try {
			stop = false;
			InputStreamReader reader = new InputStreamReader(inputStream, Charset.defaultCharset());
			while (!stop) {
				if (reader.ready()) {
					char currentChar = (char)reader.read();
					if (currentChar == EOT) break;
					consumer.accept("" + currentChar);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestStop() {
		this.stop = true;
	}
	
}
