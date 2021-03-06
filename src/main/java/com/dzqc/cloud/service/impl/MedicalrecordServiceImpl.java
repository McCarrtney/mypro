package com.dzqc.cloud.service.impl;

import com.dzqc.cloud.dao.MedicalrecordMapper;
import com.dzqc.cloud.dao.PrescriptionMapper;
import com.dzqc.cloud.dao.UserinfoMapper;
import com.dzqc.cloud.entity.Medicalrecord;
import com.dzqc.cloud.entity.Prescription;
import com.dzqc.cloud.service.MedicalrecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicalrecordServiceImpl implements MedicalrecordService {
    @Autowired
    private MedicalrecordMapper medicalrecordMapper;
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private PrescriptionMapper prescriptionMapper;

    public Integer insertMedicalrecord(Medicalrecord medicalrecord){
            return this.medicalrecordMapper.insert(medicalrecord);
    }

    @Override
    public PageInfo<Medicalrecord> selectByPage(String username, String seektime, Integer page, Integer limit) {

        PageHelper.startPage(page,limit);
        List<Medicalrecord> medicalrecords = medicalrecordMapper.selectByAll(username,seektime);
        return new PageInfo<Medicalrecord>(medicalrecords);
    }



    @Override
    public Medicalrecord selectById(Integer id) {
        return medicalrecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void batchDelete(Integer[] ids) {
        for (Integer id : ids) {
            this.medicalrecordMapper.updateStateByPrimaryKey(id);
        }
    }

    @Override
    public void update(Medicalrecord medicalrecord) {
        this.medicalrecordMapper.updateByPrimaryKeySelective(medicalrecord);
        this.userinfoMapper.updateByPrimaryKeySelective(medicalrecord.getUserinfo());
    }

    @Override
    public List<Prescription> selectPrescription(Integer rid) {
        return prescriptionMapper.selectByRecordID(rid);
    }

    @Override
    public List<Medicalrecord> selectByUserID(Integer uid) {
        return medicalrecordMapper.selectByUserID(uid);
    }

    @Override
    public List<Medicalrecord> selectByDoctorID(Integer did) {
        return medicalrecordMapper.selectByDoctorID(did);
    }

    @Override
    public Integer insertPrescription(Prescription prescription) {
        return prescriptionMapper.insert(prescription);
    }

    @Override
    public Integer updateMedicalRecord(Medicalrecord medicalrecord) {
        return medicalrecordMapper.updateByPrimaryKeySelective(medicalrecord);
    }

    @Override
    public Integer deletePrescription(Integer id) {
        return prescriptionMapper.deleteByPrimaryKey(id);
    }
}
