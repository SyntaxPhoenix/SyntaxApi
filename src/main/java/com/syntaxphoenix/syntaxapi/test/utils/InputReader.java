package com.syntaxphoenix.syntaxapi.test.utils;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @author Lauriichen
 *
 */
public class InputReader extends Thread {

	private Scanner scanner;
	private Executor other;
	private Consumer<String> action;

	private boolean started = false;
	private boolean online = true;
	private boolean command = true;

	/**
	 * 
	 */
	public InputReader(Executor executor, Consumer<String> input, InputStream stream, String name) {
		scanner = new Scanner(stream);
		other = executor;
		action = input;
		setName(name);
	}

	@Override
	public void run() {
		while (online) {
			if (scanner.hasNextLine()) {
				String input = scanner.nextLine();
				if (command) {
					other.execute(() -> action.accept(input));
				}
			}
		}
	}

	public void setCommand(boolean command) {
		this.command = command;
	}

	public boolean getCommand() {
		return command;
	}

	public boolean isOnline() {
		return online;
	}
	
	public void setAction(Consumer<String> action) {
		this.action = action;
	}
	
	public Consumer<String> getAction() {
		return action;
	}
	
	public InputReader initialize() {
		start();
		return this;
	}
	
	public void start() {
		if(!started) {
			super.start();
		}
	}
	
	public void shutdown() {
		interrupt();
	}

	public void interrupt() {
		if(online) {
			online = false;
			super.interrupt();
		}
	}

}
