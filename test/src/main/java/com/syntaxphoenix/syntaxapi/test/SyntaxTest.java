package com.syntaxphoenix.syntaxapi.test;

import java.util.concurrent.Executor;

import com.syntaxphoenix.syntaxapi.test.menu.MainMenu;
import com.syntaxphoenix.syntaxapi.test.menu.menus.TestMenu;
import com.syntaxphoenix.syntaxapi.test.tests.ColorTest;
import com.syntaxphoenix.syntaxapi.test.tests.DownloadTest;
import com.syntaxphoenix.syntaxapi.test.tests.LoggerTest;
import com.syntaxphoenix.syntaxapi.test.tests.NbtTest;
import com.syntaxphoenix.syntaxapi.test.tests.RandomChooseTest;
import com.syntaxphoenix.syntaxapi.test.tests.RandomTest;
import com.syntaxphoenix.syntaxapi.test.tests.RequestTest;
import com.syntaxphoenix.syntaxapi.test.tests.ServiceTest;
import com.syntaxphoenix.syntaxapi.test.tests.SocketTest;
import com.syntaxphoenix.syntaxapi.test.tests.ThreadTest;
import com.syntaxphoenix.syntaxapi.test.tests.VersionTest;
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
        (getMenu()).open((reader = new InputReader(this, input -> {
        }, System.in, "ConsoleReader")).initialize());
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

        menu.register("VersionTest", "Tests the Version Manager", new VersionTest());
        menu.register("ServiceTest", "Tests RNG", new ServiceTest());
        menu.register("ColorTest", "Tests java.awt.Color", new ColorTest());
        menu.register("LoggerTest", "Tests the new Logger", new LoggerTest());
        menu.register("NbtTest", "Tests the new NbtLib", new NbtTest());
        menu.register("RandomTest", "Tests the Random things", new RandomTest());
        menu.register("RandomChooseTest", "Tests the Random Choose thing", new RandomChooseTest());
        menu.register("DownloadTest", "Tests the DownloadService", new DownloadTest());
        menu.register("ThreadTest", "Tests the new Threading Stuff", new ThreadTest(), "amount=<Number>", "threadsMin=<Number>",
            "threadsMax=<Number>");
        menu.register("SocketTest", "Tests the socket server", new SocketTest(), "port=<Number>");
        menu.register("RequestTest", "Tests the Request util", new RequestTest());

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
        if (menu == null) {
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
