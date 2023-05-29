package com.example.warehouse2.adapter;

import java.io.Serializable;

public class ListItemN implements Serializable {
    private String title;
    private String unit_measurement;
    private String uri = "empty";
    private int id = 0;

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit_measurement() {
        return unit_measurement;
    }

    public void setUnit_measurement(String unit_measurement) {
        this.unit_measurement = unit_measurement;
    }
}
