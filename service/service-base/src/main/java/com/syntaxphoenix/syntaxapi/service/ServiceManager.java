package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.utils.general.Status;

public class ServiceManager {

    private final LinkedList<ServiceContainer> containers = new LinkedList<>();
    private final LinkedList<IService> services = new LinkedList<>();
    private ILogger logger;

    public ServiceManager() {
        this(null);
    }

    public ServiceManager(ILogger logger) {
        this.logger = logger;
    }

    /*
     * Logger
     */

    public ILogger getLogger() {
        return logger;
    }

    public boolean hasLogger() {
        return logger != null;
    }

    public ServiceManager setLogger(ILogger logger) {
        this.logger = logger;
        return this;
    }

    /*
     * Service Handling
     */

    public boolean register(IService service) {
        if (isRegistered(service)) {
            return false;
        }
        return services.add(service);
    }

    public boolean isRegistered(IService service) {
        return isRegistered(service.getId());
    }

    public boolean isRegistered(String id) {
        return services.stream().anyMatch(service -> service.getId().equals(id));
    }

    public IService getService(String id) {
        Optional<IService> option = findService(id);
        return option.isPresent() ? option.get() : null;
    }

    public List<IService> getServices() {
        return services.stream().collect(Collectors.toList());
    }

    public Optional<IService> findService(String id) {
        return services.stream().filter(service -> service.getId().equals(id)).findAny();
    }

    public boolean unregister(IService service) {
        return unregister(service.getId());
    }

    public boolean unregister(String id) {
        Optional<IService> option = findService(id);
        if (!option.isPresent()) {
            return false;
        }
        return services.remove(option.get());
    }

    /*
     * Execution
     */

    public Status run(String id) {
        Optional<IService> option = findService(id);
        if (option.isPresent()) {
            return run(option.get());
        }
        return Status.EMPTY;
    }

    public Status run(IService service) {
        return service.execute(this);
    }

    /*
     * Subscription
     */

    // subscribe

    public void subscribe(Object object) {
        boolean flag = object instanceof Class;
        Class<?> clazz = flag ? (Class<?>) object : object.getClass();
        ArrayList<Field> fields = ServiceAnalyser.findFields(flag, clazz);
        ArrayList<Method> methods = ServiceAnalyser.findMethods(flag, clazz);

        if (hasLogger()) {
            if (logger.getState().extendedInfo()) {
                logger.log("Searching for Subscribtions in " + clazz + " (" + (flag ? "static" : "non-static") + ')');
                logger.log("Found " + fields.size() + " Fields");
                logger.log("Found " + methods.size() + " Methods");
            }
        }

        ServiceContainer container = new ServiceContainer(object);
        if (!fields.isEmpty()) {
            for (Field field : fields) {
                container.add(flag ? new ServiceFieldValue(clazz, field) : new ServiceFieldValue(clazz, field, object));
            }
        }

        if (!methods.isEmpty()) {
            for (Method method : methods) {
                container.add(flag ? new ServiceMethodValue(clazz, method) : new ServiceMethodValue(clazz, method, object));
            }
        }

        if (container.isEmpty()) {
            return;
        }
        containers.add(container);
    }

    // Unsubscribe

    public void unsubscribe(ServiceContainer container) {
        if (containers.isEmpty()) {
            return;
        }
        containers.remove(container);
    }

    public void unsubscribe(Object object) {
        ServiceContainer[] containers = this.containers.stream().filter(container -> container.getOwner().equals(object))
            .toArray(size -> new ServiceContainer[size]);
        if (containers.length == 0) {
            return;
        }
        for (ServiceContainer container : containers) {
            this.containers.remove(container);
        }
    }

    public void unsubscribe(Class<?> clazz) {
        ServiceContainer[] containers = this.containers.stream().filter(container -> container.getOwner().getClass() == clazz)
            .toArray(size -> new ServiceContainer[size]);
        if (containers.length == 0) {
            return;
        }
        for (ServiceContainer container : containers) {
            this.containers.remove(container);
        }
    }

    // ServiceContainer Getter

    public List<ServiceContainer> getContainers() {
        return containers.stream().collect(Collectors.toList());
    }

    // Subscription Getter

    public IServiceValue[] getSubscriptions(String id) {
        Optional<IService> service = findService(id);
        return service.isPresent() ? getSubscriptions(service.get()) : new IServiceValue[0];
    }

    public IServiceValue[] getSubscriptions(IService service) {
        return getSubscriptions(service.getOwner());
    }

    public IServiceValue[] getSubscriptions(Class<? extends IService> service) {
        if (containers.isEmpty()) {
            return new IServiceValue[0];
        }

        ArrayList<IServiceValue> services = new ArrayList<>();
        for (ServiceContainer container : containers) {
            services.addAll(Arrays.asList(container.getValues(service)));
        }

        return services.toArray(new IServiceValue[0]);
    }

    public IServiceValue[] getSubscriptions(String id, ValueType type) {
        Optional<IService> service = findService(id);
        return service.isPresent() ? getSubscriptions(service.get(), type) : new IServiceValue[0];
    }

    public IServiceValue[] getSubscriptions(IService service, ValueType type) {
        return getSubscriptions(service.getOwner(), type);
    }

    public IServiceValue[] getSubscriptions(Class<? extends IService> service, ValueType type) {
        IServiceValue[] valueArray = getSubscriptions(service);
        if (valueArray.length == 0) {
            return valueArray;
        }
        List<IServiceValue> values = Arrays.asList(valueArray);
        return values.stream().filter(value -> value.getType() == type).toArray(size -> new IServiceValue[size]);
    }

}
