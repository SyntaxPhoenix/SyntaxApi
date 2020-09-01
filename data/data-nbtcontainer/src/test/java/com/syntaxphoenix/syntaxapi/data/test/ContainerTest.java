package com.syntaxphoenix.syntaxapi.data.test;

import org.junit.jupiter.api.Test;

import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtAdapterRegistry;
import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtContainer;

public class ContainerTest {
	
	@Test
	public void testData() {
		
		NbtAdapterRegistry registry = new NbtAdapterRegistry();
		
		NbtContainer container = new NbtContainer(registry);
		
		container.set(int.class.getSimpleName(), 9350485);
		container.set(long.class.getSimpleName(), 93504850000000000L);
		container.set(float.class.getSimpleName(), 9350485.45658F);
		container.set(double.class.getSimpleName(), 93504850000.5465886456454864D);
		
		container.set(boolean.class.getSimpleName(), true);
		container.set(String.class.getSimpleName(), "nice");
		
		print(container, int.class);
		print(container, long.class);
		print(container, float.class);
		print(container, double.class);

		print(container, boolean.class);
		print(container, String.class);
		
	}
	
	private void print(NbtContainer container, Class<?> clazz) {
		System.out.println(clazz.getSimpleName() + " => " + container.get(clazz.getSimpleName()));
	}
	
}
