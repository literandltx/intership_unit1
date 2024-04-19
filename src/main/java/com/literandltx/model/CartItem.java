package com.literandltx.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItem {
    private String title;
    private String description;
    private String group;
    private String labels;
    private Double rank;

    public CartItem() {

    }

    public CartItem(String title, String description, String group, String labels, Double rank) {
        this.title = title;
        this.description = description;
        this.group = group;
        this.labels = labels;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", group='" + group + '\'' +
                ", labels='" + labels + '\'' +
                ", rank=" + rank +
                '}';
    }
}

