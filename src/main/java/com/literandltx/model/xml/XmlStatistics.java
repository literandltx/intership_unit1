package com.literandltx.model.xml;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@XmlRootElement(name = "statistics")
public class XmlStatistics {
    private List<XmlItem> items;

    @XmlElement(name = "item")
    public void setItems(List<XmlItem> items) {
        this.items = items;
    }
}
