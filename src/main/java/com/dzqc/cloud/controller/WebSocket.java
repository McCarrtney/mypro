package com.dzqc.cloud.controller;

import com.dzqc.cloud.common.utils.CurPool;
import com.dzqc.cloud.common.utils.JsonUtils;
import com.dzqc.cloud.common.utils.SpringContextUtil;
import com.dzqc.cloud.dao.MsgInfoMapper;
import com.dzqc.cloud.dao.SeesionListMapper;
import com.dzqc.cloud.dao.UserMapper;
import com.dzqc.cloud.entity.MsgInfo;
import com.dzqc.cloud.entity.Userinfo;
import com.dzqc.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@Component
@ServerEndpoint("/websocket/{phone1}/{phone2}")
//此注解相当于设置访问URL
public class WebSocket {

    @Autowired
    private SeesionListMapper seesionListMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MsgInfoMapper msgInfoMapper;


    private static UserService userService;

    private Session session;

    @Autowired
    public void setUserService(UserService userService) {
        WebSocket.userService=userService;
    }

    @OnOpen
    public void onOpen(Session session,@PathParam(value="phone1")String phone1, @PathParam(value="phone2")String phone2) {
        if (seesionListMapper == null){
            this.seesionListMapper = (SeesionListMapper)SpringContextUtil.getBean("seesionListMapper");
        }
        if (userMapper == null){
            this.userMapper = (UserMapper)SpringContextUtil.getBean("userMapper");
        }
        if (msgInfoMapper == null){
            this.msgInfoMapper = (MsgInfoMapper)SpringContextUtil.getBean("msgInfoMapper");
        }
        this.session = session;
        Userinfo user=userService.selectByPhone(phone1);
        Userinfo toUser=userService.selectByPhone(phone2);
        Integer userId=user.getId();
        Integer toUserId=toUser.getId();
        Integer sessionId=seesionListMapper.selectIdByUser(userId,toUserId);
        CurPool.webSockets.put(userId,this);
        List<Object> list = new ArrayList<>();
        list.add(sessionId);
        list.add(session);
        CurPool.sessionPool.put(userId , list);
        System.out.println("[websocket statistics]New connection, total connections:"+CurPool.webSockets.size());
    }

    @OnClose
    public void onClose() {
        // disconnect, and remove session from sessionPool
        String phone1 = this.session.getRequestParameterMap().get("phone1").get(0);
        Userinfo user=userService.selectByPhone(phone1);
        Integer userId=user.getId();
        CurPool.sessionPool.remove(userId);
        CurPool.webSockets.remove(userId);
        System.out.println("[websocket statistics]Disconnected, now total connections:"+CurPool.webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        if (seesionListMapper == null){
            this.seesionListMapper = (SeesionListMapper)SpringContextUtil.getBean("seesionListMapper");
        }
        if (userMapper == null){
            this.userMapper = (UserMapper)SpringContextUtil.getBean("userMapper");
        }
        if (msgInfoMapper == null){
            this.msgInfoMapper = (MsgInfoMapper)SpringContextUtil.getBean("msgInfoMapper");
        }
        String phone1 = this.session.getRequestParameterMap().get("phone1").get(0);
        String phone2 = this.session.getRequestParameterMap().get("phone2").get(0);
        Userinfo user=userService.selectByPhone(phone1);
        Userinfo toUser=userService.selectByPhone(phone2);
        Integer userId=user.getId();
        Integer toUserId=toUser.getId();
        Integer sessionId=seesionListMapper.selectIdByUser(userId,toUserId);
        if (sessionId == null){
            System.out.println("Fatal error: try sending message before session established.");
            return;
        }
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setContent(message);
        msgInfo.setCreateTime(new Date());
        msgInfo.setFromUserId(userId);
        msgInfo.setFromUserName(user.getUsername());
        msgInfo.setToUserId(toUserId);
        msgInfo.setToUserName(toUser.getUsername());
        msgInfo.setUnReadFlag(0);

        msgInfoMapper.insert(msgInfo);

        //send message
        List<Object> list = CurPool.sessionPool.get(toUserId);
        if (list == null || list.isEmpty()){
            seesionListMapper.addUnReadCount(toUserId,userId);
        }else{
            // 用户存在，判断会话是否存在
            // 会话存在直接发送消息
            sendTextMessage(toUserId,JsonUtils.objectToJson(msgInfo));
        }
        System.out.println("[websocket statistics]Message from client: "+message);
    }

    // 此为广播消息
//    public void sendAllMessage(String message) {
//        for(WebSocket webSocket : webSockets) {
//            System.out.println("【websocket消息】广播消息:"+message);
//            try {
//                webSocket.session.getAsyncRemote().sendText(message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // 此为单点消息 (发送文本)
    public void sendTextMessage(Integer userId, String message) {
        Session session = (Session)CurPool.sessionPool.get(userId).get(1);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息 (发送对象)
//    public void sendObjMessage(String sessionId, Object message) {
//        Session session = CurPool.sessionPool.get(sessionId);
//        if (session != null) {
//            try {
////                session.getAsyncRemote().sendObject(message);
//                session.getBasicRemote().sendText(JsonUtils.objectToJson(message));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}