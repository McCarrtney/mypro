package com.dzqc.cloud.dao;

import com.dzqc.cloud.entity.HealthRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HealthRecord record);

    int insertSelective(HealthRecord record);

    HealthRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HealthRecord record);

    int updateByPrimaryKey(HealthRecord record);

    HealthRecord selectByUserID(Integer uid);
}
