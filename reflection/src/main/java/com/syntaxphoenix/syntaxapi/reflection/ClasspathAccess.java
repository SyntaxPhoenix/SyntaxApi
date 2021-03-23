package com.syntaxphoenix.syntaxapi.reflection;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class ClasspathAccess {

    private static final HashMap<String, ClasspathAccess> CLASSPATHS = new HashMap<>();

    public static ClasspathAccess of(String name) {
        File directory = getClasspath(name);
        if (directory == null) {
            return null;
        }
        String pathString = directory.getPath();
        if (CLASSPATHS.containsKey(pathString)) {
            return CLASSPATHS.get(pathString);
        }
        ClasspathAccess access = new ClasspathAccess(pathString, directory);
        CLASSPATHS.put(pathString, access);
        return access;
    }

    public static ClasspathAccess of(String name, ClassLoader... loaders) {
        File directory = getClasspath(name);
        if (directory == null) {
            return null;
        }
        String pathString = directory.getPath();
        if (CLASSPATHS.containsKey(pathString)) {
            return CLASSPATHS.get(pathString);
        }
        ClasspathAccess access = new ClasspathAccess(pathString, directory);
        CLASSPATHS.put(pathString, access);
        return access;
    }

    /*
     * 
     */

    private static File getClasspath(String packageName, ClassLoader... loaders) {
        File directory = null;
        try {
            File[] files = ReflectionTools.getUrlAsFiles(ReflectionHelper.forPackage(packageName, loaders));
            if (files.length == 0) {
                return null;
            }
            directory = files[0];
        } catch (URISyntaxException e) {
        }
        if (directory == null) {
            return null;
        }
        return directory;
    }

    /*
     * 
     */

    protected final HashMap<String, PackageAccess> packages = new HashMap<>();
    protected final String classPath;
    protected final File directory;

    private ClasspathAccess(String classPath, File directory) {
        this.classPath = classPath;
        this.directory = directory;
    }

    public Optional<PackageAccess> getOptionalPackage(String packageName) {
        synchronized (packages) {
            if (packages.containsKey(packageName)) {
                return Optional.of(packages.get(packageName));
            }
        }
        String[] path = packageName.split("\\.");
        ArrayList<File[]> files = new ArrayList<>();
        File[] directories = Arrays.stream(directory.listFiles(file -> file.isDirectory()))
            .filter(file -> !file.getName().equals("META-INF")).toArray(File[]::new);
        files.add(directories);
        for (int index = 0; index < (path.length - 1); index++) {
            files.add(directories = Arrays.stream(directories).map(file -> file.listFiles(current -> current.isDirectory()))
                .flatMap(array -> Arrays.stream(array)).toArray(File[]::new));
        }
        int pathLength = classPath.length() + 1;
        String filter = packageName.replace('.', '\\');
        Optional<File> option = files.stream().flatMap(array -> Arrays.stream(array)).filter(file -> {
            String filePath = file.getPath();
            return filter.equals(filePath.substring(pathLength, filePath.length()));
        }).findFirst();
        if (!option.isPresent()) {
            return Optional.empty();
        }
        PackageAccess access = new PackageAccess(this, packageName, option.get());
        packages.put(packageName, access);
        return Optional.of(access);
    }

    public PackageAccess getPackage(String packageName) {
        return getOptionalPackage(packageName).orElse(null);
    }

    public PackageAccess[] getCachedPackages() {
        synchronized (packages) {
            return packages.values().toArray(new PackageAccess[0]);
        }
    }

}
