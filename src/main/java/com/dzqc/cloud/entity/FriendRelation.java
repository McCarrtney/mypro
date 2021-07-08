package com.dzqc.cloud.entity;

import java.io.Serializable;

public class FriendRelation implements Serializable {
    private Integer id;
    private String phone1;
    private String phone2;

    private static final long serialVersionUID = 1L;

    public FriendRelation(Integer id, String phone1, String phone2) {
        this.id = id;
        this.phone1=phone1;
        this.phone2=phone2;
    }

    public FriendRelation() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

}