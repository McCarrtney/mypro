package com.dzqc.cloud.controller;

import com.dzqc.cloud.common.ResultObject;
import com.dzqc.cloud.common.bean.AjaxResult;
import com.dzqc.cloud.dao.SeesionListMapper;
import com.dzqc.cloud.dao.UserMapper;
import com.dzqc.cloud.entity.SessionList;
import com.dzqc.cloud.entity.User;
import com.dzqc.cloud.entity.Userinfo;
import com.dzqc.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SeesionListMapper seesionListMapper;

    @Autowired
    private UserService userService;

    /**
     * 已建立会话
     */
    @GetMapping("/sessionList/already")
    public ResultObject sessionListAlready(@RequestParam String phone){
        Userinfo user=userService.selectByPhone(phone);
        Integer id=user.getId();
        List<SessionList> sessionLists = seesionListMapper.selectByUserId(id);
        return  ResultObject.success(sessionLists);
    }

    // 可建立会话的用户
    //never used henceforth
    @GetMapping("/sessionList/not")
    public AjaxResult<?> sessionListNot(@RequestParam Integer id){
        List<Integer> list = seesionListMapper.selectUserIdByUserId(id);
        list.add(id);
        List<User> cloudList = userMapper.getCloudList(list);
        return AjaxResult.success(cloudList);
    }

    // 创建会话

    @GetMapping("/createSession")
    public ResultObject createSession(@RequestParam String phone1,@RequestParam String phone2){
        Userinfo user1=userService.selectByPhone(phone1);
        Userinfo user2=userService.selectByPhone(phone2);
        Integer id=user1.getId();
        Integer toUserId=user2.getId();
        Integer sessionId=seesionListMapper.selectIdByUser(id,toUserId);
        if (sessionId!=null) {
            return ResultObject.error("Session already established!");
        }
        SessionList sessionList = new SessionList();
        sessionList.setUserId(id);
        sessionList.setUnReadCount(0);
        sessionList.setListName(user2.getUsername());
        sessionList.setToUserId(toUserId);
        seesionListMapper.insert(sessionList);
        // 判断对方和我建立会话没有？ 没有也要建立
        Integer integer = seesionListMapper.selectIdByUser(toUserId, id);
        if (integer == null || integer <= 0){
            sessionList.setUserId(toUserId);
            sessionList.setToUserId(id);
            sessionList.setListName(user1.getUsername());
            seesionListMapper.insert(sessionList);
        }
        return ResultObject.success("Dual sessions established.");
    }



    // 删除会话
    @GetMapping("/delSession")
    public ResultObject delSession(@RequestParam String phone1,@RequestParam String phone2){
        Userinfo user1=userService.selectByPhone(phone1);
        Userinfo user2=userService.selectByPhone(phone2);
        Integer id=user1.getId();
        Integer toUserId=user2.getId();
        Integer sessionId=seesionListMapper.selectIdByUser(id,toUserId);
        Integer sessionId2=seesionListMapper.selectIdByUser(toUserId,id);
        if (sessionId==null||sessionId2==null) {
            return ResultObject.error("No such session found.");
        } else {
            seesionListMapper.deleteByPrimaryKey(sessionId);
            seesionListMapper.deleteByPrimaryKey(sessionId2);
            return ResultObject.success("Dual sessions deleted.");
        }
    }

}
