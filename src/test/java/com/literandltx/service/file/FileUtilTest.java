package com.literandltx.service.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileUtilTest {
    private static final String TEST_FOLDER = "testFolder";

    @BeforeAll
    public static void setUp() {
        new File(TEST_FOLDER).mkdir();

        try {
            new File(TEST_FOLDER + "/file1.txt").createNewFile();
            new File(TEST_FOLDER + "/file2.txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown() {
        deleteFolder(new File(TEST_FOLDER));
    }

    @Test
    public void givenFolderWithFiles_whenGetFilesFromFolder_thenReturnsFilesArray() {
        // Given
        File folder = new File(TEST_FOLDER);

        // When
        File[] files = FileUtil.getFilesFromFolder(folder);

        // Then
        assertNotNull(files, "Files array should not be null");
        assertEquals(2, files.length, "Number of files should be 2");
    }

    @Test
    public void givenFolderWithoutFiles_whenGetFilesFromFolder_thenThrowsException() {
        // Given
        File emptyFolder = new File("emptyFolder");

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> FileUtil.getFilesFromFolder(emptyFolder));

        // Then
        assertEquals("No files found in folder: " + emptyFolder.getAbsolutePath(), exception.getMessage(), "Exception message should be correct");
    }

    private static void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFolder(f);
                }
            }
        }
        folder.delete();
    }
}