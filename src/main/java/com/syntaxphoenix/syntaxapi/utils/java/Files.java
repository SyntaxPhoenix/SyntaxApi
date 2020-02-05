package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Files {

	public static ArrayList<File> listFiles(File directory) {
		ArrayList<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file));
			} else {
				list.add(file);
			}
		}
		return list;
	}

	public static ArrayList<String> listFileNames(File directory, String fileEnd) {
		ArrayList<String> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFileNames(file, fileEnd));
			} else {
				if (file.getName().endsWith(fileEnd)) {
					list.add(file.getName().replace(fileEnd, ""));
				}
			}
		}
		return list;
	}

	public static ArrayList<String> listFileNames(String fileEnd, File... directories) {
		ArrayList<String> list = new ArrayList<>();
		for (File file : directories) {
			list.addAll(listFileNames(file, fileEnd));
		}
		return list;
	}

	public static ArrayList<File> listFiles(File directory, String fileEnd) {
		ArrayList<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file, fileEnd));
			} else {
				if (file.getName().endsWith(fileEnd)) {
					list.add(file);
				}
			}
		}
		return list;
	}

	public static ArrayList<File> listFilesContains(File directory, String contains) {
		ArrayList<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file, contains));
			} else {
				if (file.getName().contains(contains)) {
					list.add(file);
				}
			}
		}
		return list;
	}

	public static ArrayList<File> listFiles(File directory, String fileEnd, String containsNot) {
		ArrayList<File> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(listFiles(file, fileEnd, containsNot));
			} else {
				if (file.getName().endsWith(fileEnd)) {
					if (!file.getName().contains(containsNot)) {
						list.add(file);
					}
				}
			}
		}
		return list;
	}
	
	public static void zipFileToFolderTime(File file, File folder) {
		if (!file.exists()) {
			return;
		}

		if (folder.exists()) {
			if (!folder.isDirectory()) {
				folder.delete();
				folder.mkdirs();
			}
		} else {
			folder.mkdirs();
		}

		String zipBase = Times.getDate("_") + "-%count%.zip";
		int count = 0;

		File zipFile = new File(folder, zipBase.replace("%count%", count + ""));
		while (zipFile.exists()) {
			zipFile = new File(folder, zipBase.replace("%count%", (count++) + ""));
		}

		try {
			Zipper.zip(zipFile, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
