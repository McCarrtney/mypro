package com.dzqc.cloud.dto;

import java.io.Serializable;

public class UserLogInfo implements Serializable {
    private String phone;
    private String password;
    private String username;
    private Integer role;

    private static final long serialVersionUID = 1L;

    public UserLogInfo(String phone, String password, String username, Integer role) {
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
