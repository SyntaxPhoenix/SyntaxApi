package com.syntaxphoenix.syntaxapi.test;

import java.util.concurrent.Executor;

import com.syntaxphoenix.syntaxapi.test.menu.MainMenu;
import com.syntaxphoenix.syntaxapi.test.menu.menus.TestMenu;
import com.syntaxphoenix.syntaxapi.test.tests.ColorTest;
import com.syntaxphoenix.syntaxapi.test.tests.LoggerTest;
import com.syntaxphoenix.syntaxapi.test.tests.MeshConfigTest;
import com.syntaxphoenix.syntaxapi.test.utils.InputReader;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;

/**
 * @author Lauriichen
 *
 */
public class SyntaxTest implements Executor, Printer {
	
	private static InputReader reader;
	
	public static InputReader getReader() {
		return reader;
	}
	
	/*
	 * 
	 * Vars
	 * 
	 */

	private MainMenu menu;
	
	/*
	 * 
	 * Constructor
	 * 
	 */
	
	public SyntaxTest(String[] args) {
		(getMenu()).open((reader = new InputReader(this, input -> {}, System.in, "ConsoleReader")).initialize());
	}
	
	/*
	 * 
	 * Stuff
	 * 
	 */
	
	/**
	 * @return test menu
	 */
	private TestMenu createTests() {
		TestMenu menu = new TestMenu();
		
		menu.register("MeshConfig", "Tests the MeshConfig", new MeshConfigTest(), "Config File (optional) [file=]");
		menu.register("ColorTest", "Tests java.awt.Color", new ColorTest());
		menu.register("LoggerTest", "Tests the new Logger", new LoggerTest());
		
		return menu;
	}
	
	/*
	 * 
	 * Getter & Setter
	 * 
	 */
	
	/**
	 * @return the menu
	 */
	public MainMenu getMenu() {
		if(menu == null) {
			menu = new MainMenu();
			
			menu.register("TestMenu - open the text menu", createTests());
			
		}
		return menu;
	}
	
	/*
	 * 
	 * Executor
	 * 
	 */
	
	@Override
	public void execute(Runnable command) {
		SyntaxExecutor.queue(command);
	}
	
}
