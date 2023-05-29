package com.example.warehouse2.adapter;

import java.io.Serializable;

public class ListItemU implements Serializable {
    String unit_measurement;
    private int id = 0;

    public String getUnit_measurement() {
        return unit_measurement;
    }

    public int getId() {
        return id;
    }

    public void setUnit_measurement(String unit_measurement) {
        this.unit_measurement = unit_measurement;
    }

    public void setId(int id) {
        this.id = id;
    }

}
