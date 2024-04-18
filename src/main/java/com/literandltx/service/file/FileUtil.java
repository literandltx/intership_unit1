package com.literandltx.service.file;

import java.io.File;

public class FileUtil {
    public static File[] getFilesFromFolder(final File folder) {
        final File[] files = folder.listFiles();

        if (files != null) {
            System.out.println("Files in folder : " + folder.getAbsolutePath()); // log
            System.out.println("Files found: " + files.length); // log
        } else {
            throw new RuntimeException("No files found in folder: " + folder.getAbsolutePath()); // log
        }

        return files;
    }
}
