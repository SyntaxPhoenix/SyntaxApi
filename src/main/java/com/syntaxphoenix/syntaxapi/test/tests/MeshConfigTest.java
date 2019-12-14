package com.syntaxphoenix.syntaxapi.test.tests;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.config.bytemesh.MeshConfig;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.java.Lists;

/**
 * @author Lauriichen
 *
 */
public class MeshConfigTest implements Consumer<String[]>, Printer {
	@Override
	public void accept(String[] args) {
		try {
			execute(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void execute(String[] args) throws IOException {
		File file = null;
		if (args.length != 0) {
			for (String arg : args) {
				if (arg.startsWith("file=")) {
					file = new File(arg.replace("file=", ""));
				}
			}
		}
		if (file == null) {
			file = new File("config.rgt");
		}

		for (int times = 0; times < 3; times++) {

			MeshConfig config = new MeshConfig();
			if (file.exists()) {
				config.load(file);
			}

			print(config.check("test1", Lists.asList("test", "ja", "je test")));
			print(config.check("test2", "Striiiinnnggg"));
			print(config.check("test.3", 4525));
			
			config.save(file);
			
		}

	}

}
