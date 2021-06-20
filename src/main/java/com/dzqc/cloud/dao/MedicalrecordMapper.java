package com.dzqc.cloud.dao;

import com.dzqc.cloud.entity.Medicalrecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int updateStateByPrimaryKey(Integer id);

    int insert(Medicalrecord record);

    int insertSelective(Medicalrecord record);

    Medicalrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Medicalrecord record);

    int updateByPrimaryKey(Medicalrecord record);

    List<Medicalrecord> selectByAll(@Param("username") String username , @Param("seektime")String seektime);

    List<Medicalrecord> selectByUserID(Integer uid);

    List<Medicalrecord> selectByDoctorID(Integer uid);
}