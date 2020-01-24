package com.syntaxphoenix.syntaxapi.utils.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.syntaxphoenix.syntaxapi.utils.java.Times;

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
	
	public static void zipFileToFolderTime(File file, File folder) {
		if(!file.exists()) {
			return;
		}
		
		if(folder.exists()) {
			if(!folder.isDirectory()) {
				folder.delete();
				folder.mkdirs();
			}
		} else {
			folder.mkdirs();
		}
		
		String zipBase = Times.getDate("_") + "-%count%.zip";
		int count = 0;
		
		File zipFile = new File(folder, zipBase.replace("%count%", count + ""));
		while(zipFile.exists()) {
			zipFile = new File(folder, zipBase.replace("%count%", (count++) + ""));
		}
		
		try {
			Zipper.zip(zipFile, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
