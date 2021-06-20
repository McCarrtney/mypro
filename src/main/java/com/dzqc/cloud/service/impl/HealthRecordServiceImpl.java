package com.dzqc.cloud.service.impl;

import com.dzqc.cloud.dao.HealthRecordMapper;
import com.dzqc.cloud.entity.HealthRecord;
import com.dzqc.cloud.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HealthRecordServiceImpl implements HealthRecordService {
    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Override
    public HealthRecord selectByUserID(Integer uid) {
        return healthRecordMapper.selectByUserID(uid);
    }
}
