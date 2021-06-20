package com.dzqc.cloud.entity;

import java.io.Serializable;

public class Prescription implements Serializable {
    private Integer id;
    private Integer userid;
    private Integer recordid;
    private String medicine;
    private String description;

    private static final long serialVersionUID = 1L;

    public Prescription(Integer id, Integer userid, Integer recordid, String medicine, String description) {
        this.id = id;
        this.userid = userid;
        this.recordid = recordid;
        this.medicine = medicine;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRecordid() {
        return recordid;
    }

    public void setRecordid(Integer recordid) {
        this.recordid = recordid;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine == null ? null : description.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}
