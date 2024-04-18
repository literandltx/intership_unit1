package com.literandltx;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public void testValidInputParameters() throws IOException {
        // Create a temporary directory
        File tempDir = createTemporaryDirectory();

        String[] args = {tempDir.getAbsolutePath(), "cartItem"};
        File result = Main.checkInputParamValidationAndReturnFilesFolder(args);
        assertNotNull(result);
        assertTrue(result.isDirectory());

        // Clean up the temporary directory
        tempDir.delete();
    }

    @Test
    public void testInvalidNumberOfParameters() {
        String[] args = {"path/to/inputFolder"};
        assertThrows(RuntimeException.class, () -> {
            Main.checkInputParamValidationAndReturnFilesFolder(args);
        });
    }

    @Test
    public void testNullInputParameters() {
        String[] args = {null, "cartItem"};
        assertThrows(RuntimeException.class, () -> {
            Main.checkInputParamValidationAndReturnFilesFolder(args);
        });
    }

    @Test
    public void testInvalidDirectoryPath() {
        String[] args = {"invalidPath", "cartItem"};
        assertThrows(RuntimeException.class, () -> {
            Main.checkInputParamValidationAndReturnFilesFolder(args);
        });
    }

    private File createTemporaryDirectory() throws IOException {
        File tempDir = File.createTempFile("temp", Long.toString(System.nanoTime()));
        if (!tempDir.delete()) {
            throw new IOException("Could not delete temp file: " + tempDir.getAbsolutePath());
        }
        if (!tempDir.mkdir()) {
            throw new IOException("Could not create temp directory: " + tempDir.getAbsolutePath());
        }
        return tempDir;
    }
}