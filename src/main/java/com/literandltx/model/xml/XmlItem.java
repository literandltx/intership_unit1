package com.literandltx.model.xml;

import lombok.Getter;
import javax.xml.bind.annotation.XmlElement;

@Getter
public class XmlItem {
    private String value;
    private long count;

    @XmlElement
    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement
    public void setCount(long count) {
        this.count = count;
    }
}
