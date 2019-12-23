package com.syntaxphoenix.syntaxapi.reflections;

public class Reflect extends AbstractReflect {

	public Reflect(String classPath) {
		super(classPath);
	}
	
	public Reflect(Class<?> owner) {
		super(owner);
	}
	
}
