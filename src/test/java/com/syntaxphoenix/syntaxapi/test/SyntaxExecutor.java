package com.syntaxphoenix.syntaxapi.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.java.Exceptions;

/**
 * @author Lauriichen
 *
 */
public class SyntaxExecutor extends Thread {
	
	public static SynLogger LOGGER = new SynLogger();
	public static PrintWriter WRITER;

	private static final BlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<Runnable>();

	private static SyntaxTest test;

	public static void main(String[] args) {
		test = new SyntaxTest(args);
		
		try {
			LOGGER.setStream(WRITER = new PrintWriter(new File("debug.log"), LOGGER.getStream()));
		} catch (FileNotFoundException e) {
			LOGGER.log(e);
		}
		
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
