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
	
	public MessageConsumer(TextArea textArea) {
		super();
		this.messageQueue = new LinkedBlockingQueue<>();
		this.textArea = textArea;
	}

	@Override
	public void handle(long now) {
		List<String> messages = new ArrayList<>();
		messageQueue.drainTo(messages);
		messages.forEach(msg -> textArea.appendText("\n" + msg));
	}

	public void putMessage(String message) {
		try {
			messageQueue.put(message);
		} catch (InterruptedException e) {
			// do nothing
		}
	}

}