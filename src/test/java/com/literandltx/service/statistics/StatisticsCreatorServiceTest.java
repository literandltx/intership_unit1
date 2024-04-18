package com.literandltx.service.statistics;

import com.literandltx.generator.CartItemJsonFileGeneratorService;
import java.io.File;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsCreatorServiceTest {

    private static final String TEST_FOLDER_PATH = "testInputFolder";

    @BeforeAll
    public static void setUp() {
        new CartItemJsonFileGeneratorService().generateAndWriteJsonFiles(TEST_FOLDER_PATH, 5, 10); // Generate 5 JSON files with 10 entries each
    }

    @AfterAll
    public static void tearDown() {
        cleanUpTestFiles();
    }

    @Test
    public void testRun() {
        // Given
        File inputFolder = new File(TEST_FOLDER_PATH);
        String statisticValue = "group";

        // When
        StatisticsCreatorService statisticsCreatorService = new StatisticsCreatorService();
        String resultPath = statisticsCreatorService.run(inputFolder, statisticValue);

        // Then
        assertNotNull(resultPath);
        File resultFile = new File(resultPath);
        assertTrue(resultFile.exists());

        // Clean up
        resultFile.delete();
    }

    private static void cleanUpTestFiles() {
        File folder = new File(StatisticsCreatorServiceTest.TEST_FOLDER_PATH);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        folder.delete();
    }

}