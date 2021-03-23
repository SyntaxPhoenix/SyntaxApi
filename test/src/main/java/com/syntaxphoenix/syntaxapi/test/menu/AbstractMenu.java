package com.syntaxphoenix.syntaxapi.test.menu;

import com.syntaxphoenix.syntaxapi.test.SyntaxExecutor;
import com.syntaxphoenix.syntaxapi.test.utils.InputReader;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

/**
 * @author Lauriichen
 *
 */
public abstract class AbstractMenu implements Printer {

    InputReader reader;

    public InputReader getReader() {
        return reader;
    }

    public void open(InputReader reader) {
        privateOpen();
        (this.reader = reader).setAction(input -> {
            int select = -1;
            if (Strings.isNumeric(input)) {
                select = Integer.parseInt(input);
            }
            if (select >= getSize() || select < 0) {
                if (select == -1) {
                    print("Returning to main menu...");
                    print("");
                    SyntaxExecutor.getTest().getMenu().open(getReader());
                    getReader().setCommand(true);
                    return;
                }
                print("You cannot select a action that is not available!");
                if (getActions().length != 0) {
                    print("There are following actions that you can use:");
                    print("[-1] Return to main menu");
                    print(getActions(), "[%index%] ", true);
                }
                return;
            }
            onSelect(select);
        });
    }

    protected String[] getActions() {
        return new String[0];
    }

    void privateOpen() {
        print("Please select one of these actions:");
        print("[-1] Return to main menu");
        print(getActions(), "[%index%] ", true);
        onOpen();
    }

    protected void onOpen() {}

    protected abstract int getSize();

    protected abstract void onSelect(int selected);

}
