package com.literandltx.service.statistics;

import com.literandltx.Main;
import com.literandltx.service.file.XmlWriter;
import com.literandltx.service.parser.JsonFileParser;
import com.literandltx.service.file.FileUtil;
import com.literandltx.service.parser.XmlParser;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StatisticsCreatorService {

    public String run(final File inputFolder, final String statisticValue) {
        final ConcurrentMap<String, Long> statistic = createStatistic(inputFolder, statisticValue);

        final String xmlMap = XmlParser.convertMapToXmlString(statistic);
        final File result = new File(inputFolder.getAbsolutePath() + "/" + "statistic_by_" + statisticValue + ".xml");

        XmlWriter.writeToFile(xmlMap, result);

        return result.getAbsolutePath();
    }

    private ConcurrentMap<String, Long> createStatistic(final File folder, final String statisticValue) {
        final ExecutorService executor = Executors.newFixedThreadPool(Main.THREADS_NUMBER);
        final ConcurrentMap<String, Long> statisticsMap = new ConcurrentHashMap<>();
        final JsonFileParser jsonFileParser = new JsonFileParser(statisticsMap);

        for (final File file : FileUtil.getFilesFromFolder(folder)) {
            executor.submit(() -> {
                jsonFileParser.processFile(file, statisticValue);
                System.out.println("File " + file.getName() + " has been processed."); // log
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("Thread pool was interrupted"); // log
        }

        return statisticsMap;
    }

}
