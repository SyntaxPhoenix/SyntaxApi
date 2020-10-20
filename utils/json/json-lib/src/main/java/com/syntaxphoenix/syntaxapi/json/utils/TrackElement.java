package com.syntaxphoenix.syntaxapi.json.utils;

import java.lang.reflect.Method;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.reflection.ClassCache;
import com.syntaxphoenix.syntaxapi.utils.java.Arrays;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

public final class TrackElement {

	private final StackTraceElement element;
	private final Container<Class<?>> clazz = Container.of();
	private final Container<Method> method = Container.of();

	TrackElement(StackTraceElement element) {
		this.element = element;
	}

	public boolean isValid() {
		return element != null;
	}

	public Optional<Class<?>> getCallerClass() {
		return Optional.ofNullable(clazz.orElseGet(() -> clazz.replace(ClassCache.getOptionalClass(element.getClassName()).orElse(null)).get()));
	}

	public Optional<Method> getCallerMethod() {
		return Optional
			.ofNullable(method
				.orElseGet(() -> method
					.replace(getCallerClass()
						.map(clazz -> java.util.Arrays.stream(Arrays.merge(Method[]::new, clazz.getMethods(), clazz.getDeclaredMethods())))
						.flatMap(stream -> stream.filter(method -> method.getName().equals(element.getMethodName())).findFirst())
						.orElse(null))
					.get()));
	}

	public String getFileName() {
		return element.getFileName();
	}

	public int getLine() {
		return element.getLineNumber();
	}

	public Optional<TrackElement> asOptional() {
		return element == null ? Optional.empty() : Optional.ofNullable(this);
	}

}
