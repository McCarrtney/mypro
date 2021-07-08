package com.dzqc.cloud.dto;

import java.io.Serializable;

public class Friend implements Serializable {
    private Integer id;
    private String username;
    private String frontimg;

    private static final long serialVersionUID = 1L;

    public Friend(Integer id, String username, String frontimg) {
        this.id = id;
        this.username = username;
        this.frontimg = frontimg;
    }
    public Friend() {
        super();
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

    public String getFrontimg() {
        return frontimg;
    }

    public void setFrontimg(String frontimg) {
        this.frontimg = frontimg;
    }
}
