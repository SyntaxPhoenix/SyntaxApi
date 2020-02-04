package com.syntaxphoenix.syntaxapi.test.tests;

import java.awt.Color;
import java.util.Random;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.test.utils.Printer;

public class ColorTest implements Consumer<String[]>, Printer {

	@Override
	public void accept(String[] args) {
		
		Random random = new Random();
		
		Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		
		print(color.toString());
		
	}

}
