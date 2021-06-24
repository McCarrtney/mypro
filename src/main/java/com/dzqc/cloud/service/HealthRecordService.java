package com.dzqc.cloud.service;

import com.dzqc.cloud.entity.HealthRecord;

public interface HealthRecordService {
    public HealthRecord selectByUserID(Integer uid);

    public Integer updateHealthInfo(HealthRecord healthRecord);
}
