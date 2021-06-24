package com.dzqc.cloud.dto;

import com.dzqc.cloud.entity.Prescription;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MedicalInfo implements Serializable {
    private Integer id;
    private Date createTime;
    private String doctor;
    private String diagnosis;
    private List<Prescription> prescriptions;
    private Integer did;

    private static final long serialVersionUID = 1L;

    public MedicalInfo(Integer id, Date createTime, String doctor, String diagnosis, List<Prescription> prescriptions, Integer did) {
        this.id = id;
        this.createTime = createTime;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
        this.did = did;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }
}
