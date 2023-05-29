package com.example.warehouse2.adapter;

import java.io.Serializable;

public class ListItemR implements Serializable {
    private int id = 0;
    private String title;
    private int count;
    private int count1;
    private int count2;
    private String unit;

    public int getIdR() {
        return id;
    }

    public String getTitleR() {
        return title;
    }

    public int getCountR() {
        return count;
    }

    public int getCount1R() {
        return count1;
    }

    public int getCount2R() {
        return count2;
    }

    public String getUnitR() {
        return unit;
    }

    public void setIdR(int id) {
        this.id = id;
    }

    public void setTitleR(String title) {
        this.title = title;
    }

    public void setCountR(int count) {
        this.count = count;
    }

    public void setCount1R(int count1) {
        this.count1 = count1;
    }

    public void setCount2R(int count2) {
        this.count2 = count2;
    }

    public void setUnitR(String unit) {
        this.unit = unit;
    }

}
