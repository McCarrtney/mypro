package com.dzqc.cloud.entity;

import java.io.Serializable;
import java.util.Date;

public class HealthRecord implements Serializable {
    private Integer id;
    private Date date;
    private Double height;
    private Double weight;
    private Double highpressure;
    private Double lowpressure;
    private Double lung;
    private Integer userid;

    private static final long serialVersionUID = 1L;

    public HealthRecord(Integer id, Date date, Double height, Double weight, Double highpressure, Double lowpressure, Double lung, Integer userid) {
        this.id = id;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.highpressure = highpressure;
        this.lowpressure = lowpressure;
        this.lung = lung;
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
