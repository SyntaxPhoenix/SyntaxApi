package com.syntaxphoenix.syntaxapi.event;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public final class EventAnalyser {

    private final EventListener listener;
    private final AbstractReflect reflect;

    public EventAnalyser(EventListener listener) {
        this.listener = listener;
        this.reflect = new Reflect(listener.getClass()).searchMethodsByArguments("event", Event.class);
    }

    public AbstractReflect getReflect() {
        return reflect;
    }

    public EventListener getListener() {
        return listener;
    }

    @SuppressWarnings("unchecked")
    protected int registerEvents(EventManager manager) {
        String base = "event-";
        int current = 0;
        String name;
        HashMap<Class<? extends Event>, EventExecutor> executors = new HashMap<>();
        while (reflect.containsMethod(name = (base + current))) {
            current++;
            Method method = reflect.getMethod(name);
            EventHandler handler;
            try {
                handler = method.getAnnotation(EventHandler.class);
            } catch (NullPointerException e) {
                manager.getLogger().log(e);
                continue;
            }
            Class<? extends Event> clazz = (Class<? extends Event>) method.getParameterTypes()[0];
            if (executors.containsKey(clazz)) {
                executors.get(clazz).add(handler.priority(), new EventMethod(listener, method, handler.ignoreCancel()));
            } else {
                executors.put(clazz, new EventExecutor(manager, listener, clazz).add(handler.priority(),
                    new EventMethod(listener, method, handler.ignoreCancel())));
            }
        }
        manager.registerExecutors(executors.values());
        return current;
    }

}
