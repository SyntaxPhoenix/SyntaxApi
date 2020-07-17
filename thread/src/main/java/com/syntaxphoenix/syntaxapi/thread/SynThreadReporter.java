package com.syntaxphoenix.syntaxapi.thread;

@FunctionalInterface
public interface SynThreadReporter {

	public void catchFail(Throwable throwable, SynReportThrower threw, Thread thread, Runnable command);

}
