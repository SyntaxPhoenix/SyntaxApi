package com.syntaxphoenix.syntaxapi.test.tests;

import java.util.function.Consumer;

import org.fusesource.jansi.AnsiConsole;

import com.syntaxphoenix.syntaxapi.logging.SynLogger;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;

public class LoggerTest implements Consumer<String[]>, Printer {

	@Override
	public void accept(String[] args) {
		
		AnsiConsole.systemInstall();
		SynLogger logger = new SynLogger();
		
		logger.log("This is a test #1", "debug");
		logger.log("This is a test #2", "info");
		logger.log("This is a test #3", "warning");
		logger.log("This is a test #4", "error");
		AnsiConsole.systemUninstall();
		
	}

}
