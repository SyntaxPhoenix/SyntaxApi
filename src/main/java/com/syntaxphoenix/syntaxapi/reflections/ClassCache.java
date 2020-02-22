package com.syntaxphoenix.syntaxapi.reflections;

import java.util.HashMap;

public class ClassCache {
	
	public static final HashMap<String, Class<?>> CLASSES = new HashMap<>();
    
    public static Class<?> getClass(String classPath) {
    	if(CLASSES.containsKey(classPath)) {
    		return CLASSES.get(classPath);
    	}
        try {
        	Class<?> clz = Class.forName(classPath);
        	if(clz != null) {
        		CLASSES.put(classPath, clz);
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
