package com.syntaxphoenix.syntaxapi.threading;

@FunctionalInterface
public interface SynThreadReporter {

	public void catchFail(Throwable throwable, SynThreadPool pool, Thread thread, Runnable command);

}
