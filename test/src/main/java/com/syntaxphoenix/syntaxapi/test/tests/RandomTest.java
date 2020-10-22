package com.syntaxphoenix.syntaxapi.test.tests;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.random.NumberGeneratorType;
import com.syntaxphoenix.syntaxapi.random.RandomNumberGenerator;
import com.syntaxphoenix.syntaxapi.test.SyntaxExecutor;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.io.PrintWriter;

public class RandomTest implements Consumer<String[]>, Printer {

	@Override
	public void accept(String[] args) {
		RandomNumberGenerator generator = NumberGeneratorType.MURMUR.create();

		DecimalFormat format = new DecimalFormat(
			"0.0##########################################################################################################################################################");
		format.setRoundingMode(RoundingMode.UNNECESSARY);

		PrintWriter writer = SyntaxExecutor.WRITER;
		writer.writeFile(true);
		for (int tries = 10000000; tries > 0; tries--) {
			double db = generator.nextDouble(0, 200);

			print(format.format(db));

		}
		writer.writeFile(false);
		writer.flush();

	}

}
