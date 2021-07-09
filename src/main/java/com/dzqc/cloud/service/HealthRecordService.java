package com.dzqc.cloud.service;

import com.dzqc.cloud.entity.HealthRecord;

public interface HealthRecordService {
    public HealthRecord selectByUserID(Integer uid);

    public Integer insertHealthInfo(HealthRecord healthRecord);

    public Integer deleteHealthInfo(Integer id);

    public Integer updateHealthInfo(HealthRecord healthRecord);
}
