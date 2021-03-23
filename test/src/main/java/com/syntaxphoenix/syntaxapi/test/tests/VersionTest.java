package com.syntaxphoenix.syntaxapi.test.tests;

import java.util.Random;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.version.DefaultVersion;
import com.syntaxphoenix.syntaxapi.version.VersionManager;
import com.syntaxphoenix.syntaxapi.version.VersionState;

public class VersionTest implements Consumer<String[]>, Printer {

    @Override
    public void accept(String[] args) {

        VersionManager<DefaultVersion> manager = new VersionManager<>();

        print("Creating seed...");
        long seed = System.currentTimeMillis();
        seed = 5 << seed;
        seed *= 5;
        seed = seed >>> 2;
        print("Seed generated -> " + seed);

        for (VersionState state : VersionState.values()) {
            Random random = new Random(seed + (state.ordinal() * 32254));
            print(state.name() + " | creating versions...");
            for (int versions = 100; versions > 0; versions--) {
                DefaultVersion version = new DefaultVersion(random.nextInt(1000), random.nextInt(10000), random.nextInt(100000));
                while (manager.contains(version)) {
                    version = new DefaultVersion(random.nextInt(10000), random.nextInt(100000), random.nextInt(1000000));
                }
                manager.set(state, version, false);
            }
            print(state.name() + " | versions created");
        }

        print("Sorting versions...");
        manager.sortAll();
        print("Versions sorted");

        print("Sending results...");
        for (VersionState state : VersionState.values()) {

            print(state.name() + " | High -> " + manager.getHighestVersion(state).toString());
            print(state.name() + " | Low -> " + manager.getLowestVersion(state).toString());

        }
        print("Results sent");

        print("Clearing versions...");
        manager.clearAll();
        print("Versions cleared");

    }

}
