package com.dzqc.cloud.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class UserBasicInfo implements Serializable {
    private Integer id;
    private String username;
    @JSONField(format="yyyy-MM-dd")
    private Date birthday;
    private String picture;

    private static final long serialVersionUID = 1L;

    public UserBasicInfo(Integer id, String username, Date birthday, String picture){
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.picture = picture;
    }

    public UserBasicInfo(){super();}

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
