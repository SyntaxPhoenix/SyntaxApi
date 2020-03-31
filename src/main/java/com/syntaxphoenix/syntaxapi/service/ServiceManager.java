package com.syntaxphoenix.syntaxapi.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.Arrays;
import com.syntaxphoenix.syntaxapi.utils.java.Lists;
import com.syntaxphoenix.syntaxapi.utils.java.Reflections;

public class ServiceManager {

	private final LinkedList<ServiceContainer> containers = new LinkedList<>();
	private final LinkedList<IService> services = new LinkedList<>();

	/*
	 * 
	 */

	public boolean register(IService service) {
		if (isRegistered(service))
			return false;
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

	public Optional<IService> findService(String id) {
		return services.stream().filter(service -> service.getId().equals(id)).findAny();
	}

	public boolean unregister(IService service) {
		return unregister(service.getId());
	}

	public boolean unregister(String id) {
		Optional<IService> option = findService(id);
		if (!option.isPresent())
			return false;
		return services.remove(option.get());
	}

	/*
	 * 
	 */

	public void subscribe(Object object) {
		boolean flag = object instanceof Class;
		Class<?> clazz = flag ? (Class<?>) object : object.getClass();
		ArrayList<Field> fields = Reflections.findFieldsByAnnotation(clazz, SubscribeService.class, flag);
		ArrayList<Method> methods = Reflections.findMethodsByAnnotation(clazz, SubscribeService.class, flag);

		ServiceContainer container = new ServiceContainer(object);
		if (!fields.isEmpty()) {
			for (Field field : fields) {
				container.add(new ServiceFieldValue(field));
			}
		}
		if (!methods.isEmpty()) {
			for (Method method : methods) {
				container.add(new ServiceMethodValue(method));
			}
		}

		if (container.isEmpty())
			return;
		containers.add(container);
	}

	public void unsubscribe(Object object) {
		ServiceContainer[] containers = this.containers.stream()
				.filter(container -> container.getOwner().equals(object)).toArray(size -> new ServiceContainer[size]);
		if (containers.length == 0)
			return;
		for (ServiceContainer container : containers) {
			this.containers.remove(container);
		}
	}
	
	/*
	 * 
	 */
	
	public IServiceValue[] getSubscriptions(String id) {
		Optional<IService> service = findService(id);
		return service.isPresent() ? getSubscriptions(service.get()) : new IServiceValue[0];
	}
	
	public IServiceValue[] getSubscriptions(IService service) {
		return getSubscriptions(service.getOwner());
	}
	
	public IServiceValue[] getSubscriptions(Class<? extends IService> service) {
		if(containers.isEmpty())
			return new IServiceValue[0];
		IServiceValue[] services = new IServiceValue[0];
		for(ServiceContainer container : containers) {
			Arrays.merge(services, container.getValues(service));
		}
		return services;
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
		if(valueArray.length == 0)
			return valueArray;
		List<IServiceValue> values = Lists.asList(valueArray);
		return values.stream().filter(value -> value.getType() == type).toArray(size -> new IServiceValue[size]);
	}

}
