package com.literandltx.service.statistics;

import com.literandltx.model.CartItem;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

public class StatisticCalculator {
    private final ConcurrentMap<String, Long> statisticMap;

    public StatisticCalculator(final ConcurrentMap<String, Long> statisticMap) {
        this.statisticMap = statisticMap;
    }

    public void updateStatistics(final CartItem cartItem, final String statisticValue) {
        try {
            final Field field = CartItem.class.getDeclaredField(statisticValue);

            field.setAccessible(true);
            final String key = field.get(cartItem).toString();

            if (key.contains(",")) {
                Arrays.stream(key.split(","))
                        .forEach(i -> statisticMap.merge(i.trim(), 1L, Long::sum));
            } else {
                statisticMap.merge(key, 1L, Long::sum);
            }
        } catch (final NoSuchFieldException e) {
            System.out.println("Invalid input statistic value: " + statisticValue);
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            System.out.println("Cannot access to field value: " + statisticValue);
        }
    }

}
