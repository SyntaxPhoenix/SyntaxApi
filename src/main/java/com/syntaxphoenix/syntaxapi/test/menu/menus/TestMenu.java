package com.syntaxphoenix.syntaxapi.test.menu.menus;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.test.menu.AbstractMenu;

/**
 * @author Lauriichen
 *
 */
public class TestMenu extends AbstractMenu {
	
	private ArrayList<Test> tests = new ArrayList<>();
	
	public void register(String name, String description, Consumer<String[]> action, String... arguments) {
		if(contains(name)) {
			return;
		}
		tests.add(new Test(name, description, action, arguments));
	}
	
	public void register(Test test) {
		if(contains(test.getName())) {
			return;
		}
		tests.add(test);
	}
	
	public Optional<Test> tryGet(String name){
		return tests.stream().filter(test -> test.getName().equals(name)).findFirst();
	}
	
	public boolean contains(String name) {
		return tryGet(name).isPresent();
	}
	
	public Test get(String name) {
		return tryGet(name).get();
	}
	
	public Test get(int index) {
		return tests.get(index);
	}
	
	@Override
	protected int getSize() {
		return tests.size();
	}
	
	@Override
	protected void onSelect(int selected) {
		Test test = get(selected);
		
		String[] arguments = test.getArguments();
		if(arguments.length == 0) {
			runTest(test, arguments);
		} else {
			print("To run the test you need following arguments:");
			print(arguments, "- ", true);
			getReader().setAction(input -> runTest(test, input.split(" ")));
			return;
		}
	}
	
	private void runTest(Test test, String[] input) {
		getReader().setCommand(false);
		print("Trying to run test with your specifications...");
		space();
		test.getAction().accept(input);
		space();
		getReader().setCommand(true);
		print("Test completed!");
		print("");
		onOpen();
	}
	
	@Override
	protected String[] getActions() {
		String[] actions = new String[tests.size()];
		for(int index = 0; index < actions.length; index++) {
			Test test = get(index);
			actions[index] = test.getName() + " - " + test.getDescription();
		}
		return actions;
	}
	
	public class Test {
		
		private String name;
		private String description;
		private String[] arguments;
		private Consumer<String[]> action;
		
		Test(String name, String description, Consumer<String[]> action, String... arguments) {
			this.name = name;
			this.action = action;
			this.arguments = arguments;
			this.description = description;
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * @return the needed arguments
		 */
		public String[] getArguments() {
			return arguments == null ? new String[0] : arguments;
		}
		
		/**
		 * @return the action
		 */
		public Consumer<String[]> getAction() {
			return action;
		}
		
	}
	
}
