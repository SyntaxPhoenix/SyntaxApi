package com.syntaxphoenix.syntaxapi.reflections;

import java.util.HashMap;

public class Reflector {
	
	private static HashMap<String, Class<?>> classes = new HashMap<>();
    
    public static Class<?> getClass(String classPath) {
    	if(classes.containsKey(classPath)) {
    		return classes.get(classPath);
    	}
        try {
        	Class<?> clz = Class.forName(classPath);
        	if(clz != null) {
        		classes.put(classPath, clz);
        		return clz;
        	}
    		return null;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
