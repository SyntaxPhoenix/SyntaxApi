package com.syntaxphoenix.syntaxapi.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Files {

	public static List<File> listFiles(File directory) {
		List<File> files = new ArrayList<>();
		if (directory.isDirectory()) {
			File[] ff = directory.listFiles();
			for (int v = 0; v < ff.length; v++) {
				if (ff[v].isDirectory()) {
					files.addAll(listFiles(ff[v]));
				} else {
					files.add(ff[v]);
				}
			}
		}
		return files;
	}

	public static List<File> listFiles(File directory, String fileEnd) {
		List<File> files = new ArrayList<>();
		if (directory.isDirectory()) {
			File[] ff = directory.listFiles();
			for (int v = 0; v < ff.length; v++) {
				if (ff[v].isDirectory()) {
					files.addAll(listFiles(ff[v], fileEnd));
				} else {
					if (ff[v].getName().endsWith(fileEnd)) {
						files.add(ff[v]);
					}
				}
			}
		}
		return files;
	}

}
