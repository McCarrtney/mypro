package com.dzqc.cloud.service;

import com.dzqc.cloud.entity.HealthRecord;

import java.util.List;

public interface HealthRecordService {
    public List<HealthRecord> selectByUserID(Integer uid);

    public Integer insertHealthInfo(HealthRecord healthRecord);

    public Integer deleteHealthInfo(Integer id);

    public Integer updateHealthInfo(HealthRecord healthRecord);
}
