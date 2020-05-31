package com.syntaxphoenix.syntaxapi.threading;

@FunctionalInterface
public interface SynThreadReporter {

	public void catchFail(Throwable throwable, SynReportThrower threw, Thread thread, Runnable command);

}
