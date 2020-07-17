package com.syntaxphoenix.syntaxapi.test.menu;

import java.util.ArrayList;

import com.syntaxphoenix.syntaxapi.test.utils.InputReader;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

/**
 * @author Lauriichen
 *
 */
public class MainMenu extends AbstractMenu {

	private ArrayList<SubMenu> menus = new ArrayList<>();

	public void register(String description, AbstractMenu menu) {
		menus.add(new SubMenu(description, menu));
	}

	public SubMenu get(int selected) {
		return menus.get(selected);
	}

	@Override
	protected int getSize() {
		return menus.size();
	}

	@Override
	protected void onOpen() {
		print("Please select one of these actions:");
		print("[-1] Stop this test");
		print(getActions(), "[%index%] ", true);
	}

	@Override
	protected String[] getActions() {
		String[] actions = new String[menus.size()];
		for (int index = 0; index < actions.length; index++) {
			actions[index] = get(index).getDescription();
		}
		return actions;
	}

	@Override
	protected void onSelect(int selected) {
		menus.get(selected).getMenu().open(getReader());
	}

	public class SubMenu {

		private String description;
		private AbstractMenu menu;

		SubMenu(String description, AbstractMenu menu) {
			this.description = description;
			this.menu = menu;
		}

		public String getDescription() {
			return description;
		}

		public AbstractMenu getMenu() {
			return menu;
		}

	}
	
	/**
	 * @see com.syntaxphoenix.syntaxapi.test.menu.AbstractMenu#open(com.syntaxphoenix.syntaxapi.test.utils.InputReader)
	 */

	@Override
	public void open(InputReader reader) {
		onOpen();
		(this.reader = reader).setAction(input -> {
			int select = -1;
			if (Strings.isNumeric(input)) {
				select = Integer.parseInt(input);
			}
			if (select >= getSize() || select < 0) {
				if(select == -1) {
					print("");
					print("Goodbye!");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.exit(0);
					return;
				}
				print("You cannot select a action that is not available!");
				if (getActions().length != 0) {
					print("There are following actions that you can use:");
					print("[-1] Stop this test");
					print(getActions(), "[%index%] ", true);
				}
				return;
			}
			onSelect(select);
		});
	}

}
