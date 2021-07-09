package com.dzqc.cloud.service.impl;

import com.dzqc.cloud.dao.HealthRecordMapper;
import com.dzqc.cloud.entity.HealthRecord;
import com.dzqc.cloud.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HealthRecordServiceImpl implements HealthRecordService {
    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Override
    public List<HealthRecord> selectByUserID(Integer uid) {
        return healthRecordMapper.selectByUserID(uid);
    }

    @Override
    public Integer insertHealthInfo(HealthRecord healthRecord) {
        return healthRecordMapper.insert(healthRecord);
    }

    @Override
    public Integer deleteHealthInfo(Integer id) {
        return healthRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateHealthInfo(HealthRecord healthRecord) {
        return healthRecordMapper.updateByPrimaryKeySelective(healthRecord);
    }
}
