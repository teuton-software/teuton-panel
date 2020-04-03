package io.github.teuton.panel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.animation.AnimationTimer;
import javafx.scene.control.TextArea;

public class MessageConsumer extends AnimationTimer {
	private BlockingQueue<String> messageQueue;
	private TextArea textArea;
	private boolean newline;
	
	public MessageConsumer(TextArea textArea, boolean newline) {
		super();
		this.messageQueue = new LinkedBlockingQueue<>();
		this.textArea = textArea;
		this.newline = newline;
	}

	public MessageConsumer(TextArea textArea) {
		this(textArea, true);
	}
	
	@Override
	public void handle(long now) {
		List<String> messages = new ArrayList<>();
		messageQueue.drainTo(messages);
		messages.forEach(msg -> textArea.appendText((newline ? "\n" : "") + msg));
	}

	public void putMessage(String message) {
		try {
			messageQueue.put(message);
		} catch (InterruptedException e) {
			// do nothing
		}
	}

}