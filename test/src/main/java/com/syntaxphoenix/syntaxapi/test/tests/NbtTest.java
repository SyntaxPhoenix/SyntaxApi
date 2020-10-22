package com.syntaxphoenix.syntaxapi.test.tests;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.config.nbt.NbtConfig;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;

public class NbtTest implements Consumer<String[]>, Printer {

	@Override
	public void accept(String[] args) {

		NbtConfig config = new NbtConfig(false);

		File file = new File("C:\\Users\\laura\\Desktop\\Eclipse\\test.nbt");

		try {
			config.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		print(config.toString());

		file.delete();
		NbtTag tag = config.get("testBigInt");
		if (tag.getType() == NbtType.STRING) {
			NbtString string = (NbtString) tag;
			if (string.isBigInteger()) {
				System.out.println("BIG INT");
			}
		}

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
