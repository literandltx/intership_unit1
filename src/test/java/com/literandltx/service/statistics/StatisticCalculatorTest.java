package com.literandltx.service.statistics;

import com.literandltx.model.CartItem;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

class StatisticCalculatorTest {
    @Test
    public void givenCartItemWithGroup_whenUpdateStatistics_thenGroupCountIncreasesByOne() {
        // Given
        CartItem cartItem = new CartItem();
        cartItem.setGroup("Group A");

        ConcurrentMap<String, Long> statisticMap = new ConcurrentHashMap<>();
        StatisticCalculator statisticCalculator = new StatisticCalculator(statisticMap);

        // When
        statisticCalculator.updateStatistics(cartItem, "group");

        // Then
        assertEquals(1, statisticMap.get("Group A"));
    }

    @Test
    public void givenCartItemWithGroup_whenUpdateStatisticsTwoTimes_thenGroupCountIncreasesByTwo() {
        // Given
        CartItem cartItem1 = new CartItem();
        cartItem1.setGroup("Group A");
        CartItem cartItem2 = new CartItem();
        cartItem2.setGroup("Group A");

        ConcurrentMap<String, Long> statisticMap = new ConcurrentHashMap<>();
        StatisticCalculator statisticCalculator = new StatisticCalculator(statisticMap);

        // When
        statisticCalculator.updateStatistics(cartItem1, "group");
        statisticCalculator.updateStatistics(cartItem2, "group");

        // Then
        assertEquals(2, statisticMap.get("Group A"));
    }

    @Test
    public void givenCartItemWithGroup_whenUpdateStatisticsWithDifferentItems_thenGroupCountIncreasesIndependent() {
        // Given
        CartItem cartItem1 = new CartItem();
        cartItem1.setGroup("Group A");
        CartItem cartItem2 = new CartItem();
        cartItem2.setGroup("Group A");
        CartItem cartItem3 = new CartItem();
        cartItem3.setGroup("Group B");

        ConcurrentMap<String, Long> statisticMap = new ConcurrentHashMap<>();
        StatisticCalculator statisticCalculator = new StatisticCalculator(statisticMap);

        // When
        statisticCalculator.updateStatistics(cartItem1, "group");
        statisticCalculator.updateStatistics(cartItem2, "group");
        statisticCalculator.updateStatistics(cartItem3, "group");


        // Then
        assertEquals(2, statisticMap.get("Group A"));
        assertEquals(1, statisticMap.get("Group B"));

    }

    @Test
    public void givenCartItemWithCommaSeparatedLabels_whenUpdateStatistics_thenEachLabelCountIncreasesByOne() {
        // Given
        CartItem cartItem1 = new CartItem();
        cartItem1.setLabels("Label1, Label2");
        CartItem cartItem2 = new CartItem();
        cartItem2.setLabels("Label1, Label2, Label3");

        ConcurrentMap<String, Long> statisticMap = new ConcurrentHashMap<>();
        StatisticCalculator statisticCalculator = new StatisticCalculator(statisticMap);

        // When
        statisticCalculator.updateStatistics(cartItem1, "labels");
        statisticCalculator.updateStatistics(cartItem2, "labels");

        // Then
        assertEquals(2, statisticMap.get("Label1"));
        assertEquals(2, statisticMap.get("Label2"));
        assertEquals(1, statisticMap.get("Label3"));
    }
}