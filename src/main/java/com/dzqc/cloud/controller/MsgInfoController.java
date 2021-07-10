package com.dzqc.cloud.controller;

import com.dzqc.cloud.common.ResultObject;
import com.dzqc.cloud.dao.FriendRelationMapper;
import com.dzqc.cloud.dao.MsgInfoMapper;
import com.dzqc.cloud.dao.SeesionListMapper;
import com.dzqc.cloud.entity.MsgInfo;
import com.dzqc.cloud.entity.SessionList;
import com.dzqc.cloud.entity.Userinfo;
import com.dzqc.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dzqc.cloud.dto.MsgInfoL;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MsgInfoController {

    @Autowired
    private MsgInfoMapper msgInfoMapper;

    @Autowired
    private SeesionListMapper seesionListMapper;

    @Autowired
    private FriendRelationMapper friendRelationMapper;

    @Autowired
    private UserService userService;
    // 消息列表
    @GetMapping("/msgList")
    public ResultObject msgList(@RequestParam Integer sessionId){
        SessionList sessionList = seesionListMapper.selectByPrimaryKey(sessionId);
        if(sessionList == null){
            return ResultObject.error("Session Not Established!");
        }
        Integer fromUserId = sessionList.getUserId();
        Integer toUserId = sessionList.getToUserId();
        List<MsgInfo> msgInfoList = msgInfoMapper.selectMsgList(fromUserId,toUserId);
        // 更新消息已读
        msgInfoMapper.msgRead(fromUserId, toUserId);
        // 更新会话里面的未读消息
        seesionListMapper.delUnReadCount(fromUserId, toUserId);
        return ResultObject.success(msgInfoList);
    }

    @RequestMapping("/chat/records")
    public ResultObject findFriends(@RequestParam(name="phone1",required = true) String phone1,
                                    @RequestParam(name="phone2",required = true) String phone2){
        Userinfo user1=userService.selectByPhone(phone1);
        Userinfo user2=userService.selectByPhone(phone2);
        Integer id=user1.getId();
        Integer toid=user2.getId();
        List<MsgInfo> msgInfoList = msgInfoMapper.selectMsgList(id,toid);
        // 更新消息已读
        msgInfoMapper.msgRead(id, toid);
        // 更新会话里面的未读消息
        seesionListMapper.delUnReadCount(id, toid);
        List<MsgInfoL> msgl=new ArrayList<>();
        for (MsgInfo mi : msgInfoList) {
            Userinfo user=userService.selectByPrimaryKey(mi.getFromUserId());
            MsgInfoL ml=new MsgInfoL(mi);
            ml.setFromUserPhone(user.getPhone());
            ml.setFromUserName(user.getUsername());

            user=userService.selectByPrimaryKey(mi.getToUserId());
            ml.setToUserPhone(user.getPhone());
            ml.setToUserName(user.getUsername());
            msgl.add(ml);
        }
        return ResultObject.success(msgl);
    }
}
