package com.literandltx.service.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlWriterTest {
    @Test
    public void givenEmptyContentAndFile_whenWriteToFile_thenFileIsEmpty() {
        // Given
        String content = "";
        File file = new File("empty_file.xml");

        // When
        XmlWriter.writeToFile(content, file);

        // Then
        assertTrue(file.exists());
        assertEquals(0, file.length());

        file.delete();
    }

    @Test
    public void givenNonEmptyContentAndEmptyFile_whenWriteToFile_thenFileHasContent() {
        // Given
        String content = "Some content";
        File file = new File("non_empty_file.xml");

        // When
        XmlWriter.writeToFile(content, file);

        // Then
        assertTrue(file.exists());
        assertEquals(content, readFileContents(file.getAbsolutePath()));

        file.delete();
    }

    @Test
    public void givenEmptyContentAndNonEmptyFile_whenWriteToFile_thenFileIsEmpty() {
        // Given
        String existingContent = "Existing content";
        writeFileContents("existing_file.xml", existingContent);
        String newContent = "";
        File file = new File("existing_file.xml");

        // When
        XmlWriter.writeToFile(newContent, file);

        // Then
        assertEquals("", readFileContents(file.getAbsolutePath()));

        file.delete();
    }

    @Test
    public void givenNonEmptyContentAndFile_whenWriteToFile_thenFileHasContent() {
        // Given
        String content = "Some content";
        File file = new File("file.xml");

        // When
        XmlWriter.writeToFile(content, file);

        // Then
        assertTrue(file.exists());
        assertEquals(content, readFileContents(file.getAbsolutePath()));

        file.delete();
    }

    @Test
    public void givenFileWithWritePermissionsIssue_whenWriteToFile_thenThrowRuntimeException() {
        // Given
        String content = "Some content";
        File file = new File("/root/test.xml");

        // When-Then
        assertThrows(RuntimeException.class, () -> XmlWriter.writeToFile(content, file));

        file.delete();
    }

    @Test
    public void givenNullContent_whenWriteToFile_thenThrowNullPointerException() {
        // Given
        File file = new File("test.xml");

        // When-Then
        assertThrows(NullPointerException.class, () -> XmlWriter.writeToFile(null, file));

        file.delete();
    }

    @Test
    public void givenNullFile_whenWriteToFile_thenThrowNullPointerException() {
        // Given
        String content = "Some content";

        // When-Then
        assertThrows(NullPointerException.class, () -> XmlWriter.writeToFile(content, null));
    }

    // Helper method
    private String readFileContents(final String filename) {
        try (final BufferedReader reader = new BufferedReader(new FileReader(filename));) {
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method
    private void writeFileContents(final String filename, final String content) {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(content);
            writer.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}