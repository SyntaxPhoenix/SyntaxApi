package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Files {

    /*
     * File / Folder creation
     */

    public static File createFolder(File folder) {
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                return null;
            }
            return folder;
        } else if (!folder.isDirectory()) {
            if (folder.delete()) {
                if (!folder.mkdirs()) {
                    return null;
                }
                return folder;
            }
            return null;
        }
        return folder;
    }

    public static File createFile(File file) {
        if (file.getParent() != null && !file.getParent().trim().isEmpty()) {
            if (createFolder(file.getParentFile()) == null) {
                return null;
            }
        }

        if (!file.exists()) {
            if (!createFile0(file)) {
                return null;
            }
            return file;
        } else if (!file.isFile()) {
            if (file.delete()) {
                if (!createFile0(file)) {
                    return null;
                }
                return file;
            }
            return null;
        }
        return file;
    }

    private static boolean createFile0(File file) {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    /*
     * File Listing
     */

    public static List<File> listFiles(File directory) {
        return Arrays.stream(directory.listFiles()).collect(Collect.collectList((output, input) -> {
            if (input.isDirectory()) {
                output.addAll(listFiles(input));
            } else {
                output.add(input);
            }
        }));
    }

    public static List<File> listFiles(File directory, String fileEnd) {
        return Arrays.stream(directory.listFiles()).filter(file -> file.isDirectory() || file.getName().endsWith(fileEnd))
            .collect(Collect.collectList((output, input) -> {
                if (input.isDirectory()) {
                    output.addAll(listFiles(input));
                } else {
                    output.add(input);
                }
            }));
    }

    /*
     * Zip Files
     */

    public static void zipFileToFolderTime(File file, File folder) {
        if (!file.exists() || createFolder(folder) == null) {
            return;
        }

        String name = Times.getDate("_") + "-%count%.zip";
        int tries = 0;

        File zipFile = new File(folder, name.replace("%count%", tries + ""));
        while (zipFile.exists()) {
            zipFile = new File(folder, name.replace("%count%", (tries++) + ""));
        }

        try {
            Zipper.zip(zipFile, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
