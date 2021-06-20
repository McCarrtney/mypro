package com.dzqc.cloud.entity;

import java.io.Serializable;
import java.util.Date;

public class Empinfo implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private Date birthday;

    private String phone;

    private String office;

    private Integer roleId;

    private Integer depId;

    private Integer titleId;

    private String comment;

    private String headimg;

    private String hospital;

    private Integer state;

    private static final long serialVersionUID = 1L;

    public Empinfo(Integer id, String username, String password, Date birthday, String phone, String office, Integer roleId, Integer depId, Integer titleId, String comment, String headimg, String hospital, Integer state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.phone = phone;
        this.office = office;
        this.roleId = roleId;
        this.depId = depId;
        this.titleId = titleId;
        this.comment = comment;
        this.headimg = headimg;
        this.hospital = hospital;
        this.state = state;
    }

    public Empinfo() {
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
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getoffice() {
        return office;
    }

    public void setoffice(String office) {
        this.office = office == null ? null : office.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg == null ? null : headimg.trim();
    }

    public String gethospital() {
        return hospital;
    }

    public void sethospital(String hospital) {
        this.hospital = hospital == null ? null : hospital.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}