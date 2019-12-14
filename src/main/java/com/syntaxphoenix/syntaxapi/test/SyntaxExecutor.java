package com.syntaxphoenix.syntaxapi.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;

/**
 * @author Lauriichen
 *
 */
public class SyntaxExecutor extends Thread {

	private static final BlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<Runnable>();

	private static SyntaxTest test;

	public static void main(String[] args) {
		test = new SyntaxTest(args);

		while (true) {
			try {
				QUEUE.take().run();
			} catch (Throwable error) {
				Printer.spaces();
				Printer.prints(Exceptions.getError(error));
				Printer.spaces();
				getTest().getMenu().open(SyntaxTest.getReader());
				SyntaxTest.getReader().setCommand(true);
				continue;
			}
		}
	}

	/**
	 * @return the test
	 */
	public static SyntaxTest getTest() {
		return test;
	}

	public static void queue(Runnable command) {
		QUEUE.add(command);
	}

}
