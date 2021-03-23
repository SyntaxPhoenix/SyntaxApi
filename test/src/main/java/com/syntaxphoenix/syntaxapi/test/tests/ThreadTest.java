package com.syntaxphoenix.syntaxapi.test.tests;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.thread.SynThreadPool;

public class ThreadTest implements Consumer<String[]>, Printer {

    public int failed = 0;

    @Override
    public void accept(String[] args) {
        int amount = 10;
        int threadsMin = 1;
        int threadsMax = 4;
        if (args.length >= 1) {
            for (String arg : args) {
                if (!arg.contains("=")) {
                    continue;
                }
                String[] parts = arg.split("=");
                int value;
                try {
                    value = Integer.parseInt(parts[1]);
                } catch (Throwable thrw) {
                    value = 4;
                }
                if (parts[0].equalsIgnoreCase("amount")) {
                    amount = value;
                } else if (parts[0].equalsIgnoreCase("threads-min")) {
                    threadsMin = value;
                } else if (parts[0].equalsIgnoreCase("threads-max")) {
                    threadsMax = value;
                }
            }
        }

        SynThreadPool service = new SynThreadPool((throwable, pool, thread, command) -> {
            failed++;
        }, threadsMin, threadsMax, "ThreadTestPool");

        Random random = new Random((amount ^ threadsMax) * threadsMin);
        shouldFail = 0;

        print("Running " + amount + " commands on a ThreadPool with [min: " + threadsMin + ", max: " + threadsMax + "] Threads.");
        ArrayList<Future<?>> tasks = new ArrayList<>();
        for (int x = 0; x < amount; x++) {
            tasks.add(service.submit(createTask(random.nextBoolean())));
        }
        while (!tasks.isEmpty()) {
            Object[] object = tasks.stream().filter(future -> future.isDone()).toArray();
            for (int x = 0; x < object.length; x++) {
                tasks.remove(object[x]);
            }
        }

        service.shutdown();
        print("Test is done [success: " + (amount - failed) + ", failed: " + failed + ", shouldFail: " + shouldFail + "]");

    }

    public int shouldFail = 0;

    private Runnable createTask(boolean error) {
        if (error) {
            shouldFail++;
            return () -> {
                AbstractReflect reflect = new Reflect("fjsdfi.sdfsdfno");
                reflect.run("sdf0");
            };
        } else {
            return () -> {
            };
        }
    }

}
