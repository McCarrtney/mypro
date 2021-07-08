package com.dzqc.cloud.dao;

import com.dzqc.cloud.entity.FriendRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendRelationMapper {
    List<FriendRelation> selectFriendRelationsByPhone(String phone);
    int deleteByPhone(String phone);
    int insert(FriendRelation friendRelation);
}