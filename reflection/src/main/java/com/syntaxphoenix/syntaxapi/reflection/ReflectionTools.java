package com.syntaxphoenix.syntaxapi.reflection;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class ReflectionTools {

    public static Object getValue(Field field, Object source) {
        if (field != null) {
            boolean access = field.isAccessible();
            if (!access) {
                field.setAccessible(true);
            }
            Object output = null;
            try {
                output = field.get(source);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                if (!access) {
                    field.setAccessible(access);
                }
                e.printStackTrace();
            }
            if (!access) {
                field.setAccessible(access);
            }
            return output;
        }
        return null;
    }

    public static Object getValue(Field field) {
        return getValue(field, null);
    }

    public static boolean hasSameArguments(Class<?>[] compare1, Class<?>[] compare2) {
        if (compare1.length == 0 && compare2.length == 0) {
            return true;
        } else if (compare1.length != compare2.length) {
            return false;
        }
        for (Class<?> arg1 : compare1) {
            boolean found = true;
            for (Class<?> arg2 : compare2) {
                if (!arg1.isAssignableFrom(arg2)) {
                    found = false;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /*
     * 
     */

    public static File[] getUrlAsFiles(Collection<URL> collection) throws URISyntaxException {
        if (collection.isEmpty()) {
            return new File[0];
        }
        ArrayList<File> files = new ArrayList<>();
        for (URL url : collection) {
            files.add(new File(url.toURI().getPath()));
        }
        return files.toArray(new File[0]);
    }

    /*
     * 
     */

    public static Object execute(Object source, Method method, Object... arguments) {
        if (method != null) {
            boolean access;
            if (!(access = method.isAccessible())) {
                method.setAccessible(true);
            }
            try {
                return method.invoke(source, arguments);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                if (!access) {
                    method.setAccessible(access);
                }
                e.printStackTrace();
            }
            if (!access) {
                method.setAccessible(access);
            }
        }
        return null;
    }

    public static int countSuperTypesTill(Class<?> type, Class<?> superType) {
        int count = 0;
        Class<?> currentType = type;
        while (!superType.equals(currentType)) {
            currentType = currentType.getSuperclass();
            count++;
            if (currentType == null) {
                break;
            }
        }
        return count;
    }

}
