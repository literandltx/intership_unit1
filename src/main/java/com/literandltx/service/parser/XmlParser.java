package com.literandltx.service.parser;

import com.literandltx.model.xml.XmlItem;
import com.literandltx.model.xml.XmlStatistics;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class XmlParser {
    public static String convertMapToXmlString(final ConcurrentMap<String, Long> statisticsMap) {
        final XmlStatistics statistics = new XmlStatistics();
        final StringWriter writer = new StringWriter();
        final JAXBContext context;

        if (statisticsMap.isEmpty()) {
            return
                    """
                    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                    <statistics>
                       \s
                    </statistics>
                    """;
        }

        statistics.setItems(
                statisticsMap.entrySet().parallelStream()
                        .filter(entry -> entry.getValue() != null || entry.getKey() != null)
                        .map(entry -> {
                            final XmlItem item = new XmlItem();
                            item.setValue(entry.getKey());
                            item.setCount(entry.getValue());

                            return item;
                        })
                        .collect(Collectors.toList())
        );

        try {
            context = JAXBContext.newInstance(XmlStatistics.class);
            final Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(statistics, writer);
        } catch (final JAXBException e) {
            throw new RuntimeException(e);
        }

        return writer.toString();
    }
}
