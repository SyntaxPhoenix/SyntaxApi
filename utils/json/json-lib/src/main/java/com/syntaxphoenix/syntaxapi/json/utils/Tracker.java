package com.syntaxphoenix.syntaxapi.json.utils;

import java.lang.reflect.Method;
import java.util.Optional;

public abstract class Tracker {

	public static TrackElement getTrackElementFromStack(int offset) {
		return new TrackElement(Thread.currentThread().getStackTrace()[2 + offset]);
	}

	public static TrackElement getTrackElement() {
		return getTrackElementFromStack(1);
	}

	public static Optional<Method> getMethodFromStack(int offset) {
		TrackElement element = getTrackElementFromStack(offset + 1);
		return element.isValid() ? Optional.empty() : element.getCallerMethod();
	}

	public static Optional<Method> getCallerMethod() {
		return getMethodFromStack(1);
	}

	public static Optional<Class<?>> getClassFromStack(int offset) {
		TrackElement element = getTrackElementFromStack(offset + 1);
		return element.isValid() ? Optional.empty() : element.getCallerClass();
	}

	public static Optional<Class<?>> getCallerClass() {
		return getClassFromStack(1);
	}

}
