package com.literandltx;

import com.literandltx.service.statistics.StatisticsCreatorService;

import java.io.File;

public class Main {
    public static final int THREADS_NUMBER = 8;

    public static void main(String[] args) {
        final File inputFolder = checkInputParamValidationAndReturnFilesFolder(args);
        final String statisticValue = args[1];

        final long startTime = System.currentTimeMillis();

        final StatisticsCreatorService statisticService = new StatisticsCreatorService();
        final String resultPath = statisticService.run(inputFolder, statisticValue);

        final long endTime = System.currentTimeMillis();

        System.out.println("Execution time: " + (endTime - startTime) / 1000.0 + "sec");
        System.out.println("Result location: " + resultPath);
    }

    public static File checkInputParamValidationAndReturnFilesFolder(final String[] args) {
        if (args.length != 2) {
            throw new RuntimeException("Two parameters required");
        }

        if (args[0] == null || args[1] == null) {
            throw new RuntimeException("Invalid input parameters. Must provide a path to the input file and cartItem field.");
        }

        final File inputFolder = new File(args[0]);
        if (!inputFolder.isDirectory()) {
            throw new RuntimeException("Invalid directory path provided.");
        }

        return inputFolder;
    }
}
