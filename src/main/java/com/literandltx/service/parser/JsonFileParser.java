package com.literandltx.service.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literandltx.model.CartItem;
import com.literandltx.service.statistics.StatisticCalculator;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

public class JsonFileParser {
    private final ObjectMapper objectMapper;
    private final JsonFactory factory;
    private final StatisticCalculator statisticCalculator;

    public JsonFileParser(final ConcurrentMap<String, Long> statisticMap) {
        this.objectMapper = new ObjectMapper();
        this.factory = new JsonFactory();
        this.statisticCalculator = new StatisticCalculator(statisticMap);
    }

    public void processFile(final File file, final String statisticValue) {
        try (final JsonParser parser = factory.createParser(file)) {
            if (parser.nextToken() == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
                        final CartItem cartItem = objectMapper.readValue(parser, CartItem.class);

                        statisticCalculator.updateStatistics(cartItem, statisticValue);
                    }
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
