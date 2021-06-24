package com.dzqc.cloud.dto;

import java.io.Serializable;
import java.util.Date;

public class UserHealthInfo implements Serializable {
    private Date date;
    private Double height;
    private Double weight;
    private Double highpressure;
    private Double lowpressure;
    private Double lung;
    private Integer uid;

    private static final long serialVersionUID = 1L;

    public UserHealthInfo(Date date, Double height, Double weight, Double highpressure, Double lowpressure, Double lung, Integer uid) {
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.highpressure = highpressure;
        this.lowpressure = lowpressure;
        this.lung = lung;
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHighpressure() {
        return highpressure;
    }

    public void setHighpressure(Double highpressure) {
        this.highpressure = highpressure;
    }

    public Double getLowpressure() {
        return lowpressure;
    }

    public void setLowpressure(Double lowpressure) {
        this.lowpressure = lowpressure;
    }

    public Double getLung() {
        return lung;
    }

    public void setLung(Double lung) {
        this.lung = lung;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
