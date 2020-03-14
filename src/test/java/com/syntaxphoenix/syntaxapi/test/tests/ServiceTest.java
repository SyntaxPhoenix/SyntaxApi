package com.syntaxphoenix.syntaxapi.test.tests;

import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.random.NumberGeneratorType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;

public class ServiceTest implements Consumer<String[]>, Printer {

	@Override
	public void accept(String[] args) {
		
		long seed = 5348543785L;
		
		RandomNumberGenerator random1 = NumberGeneratorType.LINEAR.create(seed);
		RandomNumberGenerator random2 = NumberGeneratorType.PERMUTED.create(seed);
		RandomNumberGenerator random3 = NumberGeneratorType.MURMUR.create(seed);
		
		for(int x = 0; x < 500; x++) {
			print("1 - " + random1.nextInt());
			print("2 - " + random2.nextInt());
			print("3 - " + random3.nextInt());
		}
		
		for(int x = 0; x < 500; x++) {
			print("1 - " + random1.nextDouble());
			print("2 - " + random2.nextDouble());
			print("3 - " + random3.nextDouble());
		}
		
	}

}
