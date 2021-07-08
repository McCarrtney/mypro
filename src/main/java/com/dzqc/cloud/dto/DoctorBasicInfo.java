package com.dzqc.cloud.dto;

import java.io.Serializable;
import java.util.Date;

public class DoctorBasicInfo implements Serializable{
    private Integer id;
    private Date age;
    private String username;
    private String hospital;
    private String office;
    private String comment;
    private String headimg;
    private Integer state;

    private static final long serialVersionUID = 1L;

    public DoctorBasicInfo(Integer id, Date age, String username, String hospital, String office, String comment, String headimg, Integer state) {
        this.id = id;
        this.age = age;
        this.username = username;
        this.hospital = hospital;
        this.office = office;
        this.comment = comment;
        this.headimg = headimg;
        this.state = state;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
