package com.syntaxphoenix.syntaxapi.reflections;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

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
    
    public static void uncache(AbstractReflect reflect) {
    	Class<?> search = reflect.getOwner();
    	reflect.delete();
    	if(CLASSES.isEmpty())
    		return;
    	Optional<Entry<String, Class<?>>> option = CLASSES.entrySet().stream().filter(entry -> entry.getValue().equals(search)).findFirst();
    	if(option.isPresent())
    		CLASSES.remove(option.get().getKey());
    }
    
}
