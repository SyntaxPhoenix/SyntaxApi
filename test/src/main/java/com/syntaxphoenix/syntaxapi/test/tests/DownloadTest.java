package com.syntaxphoenix.syntaxapi.test.tests;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.service.ServiceManager;
import com.syntaxphoenix.syntaxapi.service.SubscribeService;
import com.syntaxphoenix.syntaxapi.service.download.Download;
import com.syntaxphoenix.syntaxapi.service.download.DownloadService;
import com.syntaxphoenix.syntaxapi.test.SyntaxExecutor;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.general.Status;

public class DownloadTest implements Consumer<String[]>, Printer {

    private final ServiceManager manager = new ServiceManager(SyntaxExecutor.LOGGER);

    public DownloadTest() {

        manager.register(new DownloadService());
        manager.subscribe(DownloadTest.class);

    }

    @Override
    public void accept(String[] args) {

        Status status = manager.run("download");

        Instant start = Instant.now();
        while (!status.isDone()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                print(e);
            }
        }

        Duration duration = Duration.between(start, Instant.now());

        print(
            "This took " + (TimeUnit.SECONDS.toMillis(duration.getSeconds()) + TimeUnit.NANOSECONDS.toMillis(duration.getNano())) + "ms!");

    }

    @SubscribeService(service = DownloadService.class, returnType = Download.class, returnsObject = true)
    public static Download prepare() {
        Download download = new Download("https://realistic.syntaxphoenix.com/downloads/RWGST/");

        download.add("BIG_OAK_TREE_1.rwgfast", "testDl/BIG_OAK_TREE_1.rwgfast");
        download.add("BIG_OAK_TREE_2.rwgfast", "testDl/BIG_OAK_TREE_2.rwgfast");
        download.add("BIG_OAK_TREE_3.rwgfast", "testDl/BIG_OAK_TREE_3.rwgfast");

        return download;
    }

}
