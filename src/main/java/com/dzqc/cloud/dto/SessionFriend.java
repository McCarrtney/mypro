package com.dzqc.cloud.dto;

import java.io.Serializable;

public class SessionFriend implements Serializable {
    Integer id;
    String phone;
    String username;
    Integer unread;

    private static final long serialVersionUID = 1L;


    public SessionFriend(Integer id, String phone, String username, Integer unread) {
        this.id = id;
        this.phone = phone;
        this.username = username;
        this.unread = unread;
    }

    public SessionFriend() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }
}
