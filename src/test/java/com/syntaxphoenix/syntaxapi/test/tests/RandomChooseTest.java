package com.syntaxphoenix.syntaxapi.test.tests;

import java.util.List;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.random.NumberGeneratorType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.java.Lists;

public class RandomChooseTest implements Consumer<String[]>, Printer {

	public static final List<String> OPTIONS = Lists.asList("Test1", "Test2", "Test3");

	@Override
	public void accept(String[] args) {

		RandomNumberGenerator generator = NumberGeneratorType.MURMUR.create();

		float timeout = 1.0f;
		while (true) {
			print(OPTIONS.get(generator.nextInt(OPTIONS.size())));
			if (timeout == 25.0f)
				break;
			try {
				Thread.sleep((int) (10 * timeout));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeout += 0.5f;
		}
		
	}

}
