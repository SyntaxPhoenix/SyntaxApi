package com.syntaxphoenix.syntaxapi.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {

	public static File[] unzip(File zip, File directory, boolean deleteZipOnEnd) throws IOException {
		if (!zip.exists() || !zip.isFile()) {
			return null;
		}
		if (!directory.exists()) {
			directory.mkdirs();
		} else if (!directory.isDirectory()) {
			return null;
		}
		byte[] buffer = new byte[2048];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
		ZipEntry entry = zis.getNextEntry();
		while (entry != null) {
			File file = new File(directory, entry.getName());
			FileOutputStream fos = new FileOutputStream(file);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			entry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		if(deleteZipOnEnd) zip.delete();
		return directory.listFiles();
	}

	public File[] unzipp(File zip, File directory, boolean deleteZipOnEnd) throws IOException {
		return Zipper.unzip(zip, directory, deleteZipOnEnd);
	}

	public static void zip(String zipName, File directory, File... toZip) throws IOException {
		if (!directory.exists()) {
			directory.mkdirs();
		} else if (!directory.isDirectory()) {
			return;
		}
		zip(new File(directory, zipName));
	}

	public void zipp(String zipName, File directory, File... toZip) throws IOException {
		Zipper.zip(zipName, directory, toZip);
	}
	
	public static void zip(File zipFile, File... toZip) throws IOException {
		if(toZip == null) return;
		if(toZip.length == 0) return;
		if (!zipFile.exists()) {
			zipFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		int failed = 0;
		for (int x = 0; x < toZip.length; x++) {
			File f = toZip[x];
			if(f == null) {
				failed += 1;
				continue;
			}
			FileInputStream fis = new FileInputStream(f);
			ZipEntry entry = new ZipEntry(f.getName());
			zipOut.putNextEntry(entry);
			byte[] bytes = new byte[2048];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			fis.close();
		}
		zipOut.close();
		fos.close();
		if(failed == toZip.length) {
			zipFile.delete();
		}
	}

	public void zipp(File zipFile, File... toZip) throws IOException {
		Zipper.zip(zipFile, toZip);
	}

}
