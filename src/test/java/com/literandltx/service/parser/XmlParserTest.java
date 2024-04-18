package com.literandltx.service.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

class XmlParserTest {
    @Test
    public void givenEmptyMap_whenConvertMapToXmlString_thenReturnsEmptyXml() {
        // Given
        ConcurrentMap<String, Long> emptyMap = new ConcurrentHashMap<>();

        // When
        String xmlString = XmlParser.convertMapToXmlString(emptyMap);

        // Then
        assertXmlStructure(xmlString, 0);
    }

    @Test
    public void givenNonEmptyMap_whenConvertMapToXmlString_thenReturnsValidXml() {
        // Given
        ConcurrentMap<String, Long> nonEmptyMap = new ConcurrentHashMap<>();
        nonEmptyMap.put("key1", 10L);
        nonEmptyMap.put("key2", 20L);

        // When
        String xmlString = XmlParser.convertMapToXmlString(nonEmptyMap);

        // Then
        assertXmlStructure(xmlString, 2);
    }

    @Test
    public void givenMapWithEmptyStringValues_whenConvertMapToXmlString_thenHandlesEmptyStringValues() {
        // Given
        ConcurrentMap<String, Long> mapWithEmptyStringValues = new ConcurrentHashMap<>();
        mapWithEmptyStringValues.put("key1", 0L);
        mapWithEmptyStringValues.put("key2", 20L);

        // When
        String xmlString = XmlParser.convertMapToXmlString(mapWithEmptyStringValues);

        // Then
        assertXmlStructure(xmlString, 2);
    }

    private void assertXmlStructure(String xmlString, int expectedItemCount) {
        Document document = Jsoup.parse(xmlString);
        Element statisticsElement = document.selectFirst("statistics");
        assertNotNull(statisticsElement);

        Elements itemElements = statisticsElement.select("item");
        assertEquals(expectedItemCount, itemElements.size());

        for (Element itemElement : itemElements) {
            Element valueElement = itemElement.selectFirst("value");
            assertNotNull(valueElement);

            Element countElement = itemElement.selectFirst("count");
            assertNotNull(countElement);
        }
    }
}