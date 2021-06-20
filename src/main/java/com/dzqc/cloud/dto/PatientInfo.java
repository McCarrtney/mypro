package com.dzqc.cloud.dto;

import com.dzqc.cloud.entity.Prescription;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PatientInfo implements Serializable {
    private Date createTime;
    private String patient;
    private Integer state;
    private String diagnosis;
    private List<Prescription> prescriptions;

    private static final long serialVersionUID = 1L;

    public PatientInfo(Date createTime, String patient, Integer state, String diagnosis, List<Prescription> prescriptions) {
        this.createTime = createTime;
        this.patient = patient;
        this.state = state;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
}
