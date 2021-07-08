package com.dzqc.cloud.controller;

import com.dzqc.cloud.common.ResultObject;
import com.dzqc.cloud.dao.FriendRelationMapper;
import com.dzqc.cloud.dto.Friend;
import com.dzqc.cloud.entity.FriendRelation;
import com.dzqc.cloud.entity.Userinfo;
import com.dzqc.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FriendinfoController {
    @Autowired
    private FriendRelationMapper friendRelationMapper;

    @Autowired
    private UserService userService;

    /*
    @RequestMapping("/chat/addFriendRelation")
    public ResultObject addFriends(@RequestParam(name="token",required = true) String token,
                                   @RequestParam(name="friend_phone",required = true) String friend_phone){
        JwtTokenUtil jwt=new JwtTokenUtil();
        if (jwt.isTokenExpired(token)) {
            return ResultObject.error("登录过期，请重新登录",901);
        }
        else {
            String phone=jwt.getUsernameFromToken(token);
            FriendRelation fr=new FriendRelation();
            fr.setPhone1(phone);
            fr.setPhone2(friend_phone);
            if (friendRelationMapper.insert(fr)==0) {
                return ResultObject.error("添加好友失败，请重试或联系管理员");
            } else {
                return ResultObject.success(fr);
            }
        }
    }

     */
/*
    @RequestMapping("/chat/friends")
    public ResultObject findFriends(@RequestParam(name="token",required = true) String token){
        JwtTokenUtil jwt=new JwtTokenUtil();
        if (jwt.isTokenExpired(token)) {
            return ResultObject.error("登录过期，请重新登录",901);
        }
        else {
            String phone=jwt.getUsernameFromToken(token);

            List<FriendRelation> friends=friendRelationMapper.selectFriendRelationsByPhone(phone);
            List<Friend> friList=new ArrayList<>();
            for (FriendRelation fr : friends) {
                Userinfo userinfo = userService.selectByPhone(fr.getPhone2());
                Integer id=userinfo.getId();
                String name=userinfo.getUsername();
                String front=userinfo.getFrontimg();
                Friend fri=new Friend(id,name,front);
                friList.add(fri);
            }
            return ResultObject.success(friList);
        }
    }
 */
    @RequestMapping("/chat/friends")
    public ResultObject findFriends(@RequestParam(name="phone",required = true) String phone){
        List<FriendRelation> friends=friendRelationMapper.selectFriendRelationsByPhone(phone);
        List<Friend> friList=new ArrayList<>();
        for (FriendRelation fr : friends) {
            Userinfo userinfo = userService.selectByPhone(fr.getPhone2());
            if (userinfo!=null) {
                Integer id=userinfo.getId();
                String name=userinfo.getUsername();
                String front=userinfo.getFrontimg();
                Friend fri=new Friend(id,name,front);
                friList.add(fri);
            }
        }
        return ResultObject.success(friList);
    }

    @RequestMapping("/chat/addFriendRelation")
    public ResultObject addFriends(@RequestParam(name="phone",required = true) String phone,
                                   @RequestParam(name="friend_phone",required = true) String friend_phone){
        FriendRelation fr=new FriendRelation();
        fr.setPhone1(phone);
        fr.setPhone2(friend_phone);

        FriendRelation fr2=new FriendRelation();
        fr2.setPhone1(friend_phone);
        fr2.setPhone2(phone);
        if (friendRelationMapper.insert(fr)==0||friendRelationMapper.insert(fr2)==0) {
            return ResultObject.error("Cannot bind friend relationship!");
        } else {
            return ResultObject.success(fr);
        }
    }
}
