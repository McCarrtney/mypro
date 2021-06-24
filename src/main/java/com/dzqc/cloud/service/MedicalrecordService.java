package com.dzqc.cloud.service;

import com.dzqc.cloud.entity.Medicalrecord;
import com.dzqc.cloud.entity.Prescription;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MedicalrecordService {
    /**
     * 添加电子病历
     * @param medicalrecord
     */
    public Integer insertMedicalrecord(Medicalrecord medicalrecord);

    /**
     * 分页查询所有的电子病历
     * @param
     * @param
     * @param page
     * @param limit
     * @return
     */
    public PageInfo<Medicalrecord> selectByPage(String username, String seekTime, Integer page, Integer limit);

    public Medicalrecord selectById(Integer id);

    /**
     * 批量删除
     * @param ids
     */
    public void batchDelete(Integer[] ids);

    public void update(Medicalrecord medicalrecord);

    public List<Prescription> selectPrescription(Integer rid);

    public List<Medicalrecord> selectByUserID(Integer uid);

    public List<Medicalrecord> selectByDoctorID(Integer did);

    public Integer insertPrescription(Prescription prescription);
}
