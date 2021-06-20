package com.dzqc.cloud.dao;

import com.dzqc.cloud.entity.Prescription;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Prescription record);

    int insertSelective(Prescription record);

    Prescription selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Prescription record);

    int updateByPrimaryKey(Prescription record);

    List<Prescription> selectByRecordID(Integer rid);
}
