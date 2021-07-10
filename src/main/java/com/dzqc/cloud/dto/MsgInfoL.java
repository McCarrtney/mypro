package com.dzqc.cloud.dto;

import com.dzqc.cloud.entity.MsgInfo;

import java.io.Serializable;
import java.util.Date;

public class MsgInfoL implements Serializable {
    private Integer id;

    /**
     * 消息发送者id
     */
    private Integer fromUserId;

    private String fromUserPhone;
    /**
     * 消息发送者名称
     */
    private String fromUserName;

    /**
     * 消息接收者id
     */
    private Integer toUserId;

    /**
     * 消息接收者名称
     */
    private String toUserName;

    private String toUserPhone;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息发送时间
     */
    private Date createTime;

    /**
     * 是否已读（1 已读）
     */
    private Integer unReadFlag;

    private static final long serialVersionUID = 1L;

    public MsgInfoL(MsgInfo mi) {
        id=mi.getId();
        fromUserId=mi.getFromUserId();
        fromUserName=mi.getFromUserName();
        toUserId=mi.getToUserId();
        toUserName=mi.getToUserName();
        content=mi.getContent();
        createTime=mi.getCreateTime();
        unReadFlag=mi.getUnReadFlag();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUnReadFlag() {
        return unReadFlag;
    }

    public void setUnReadFlag(Integer unReadFlag) {
        this.unReadFlag = unReadFlag;
    }


    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserPhone() {
        return fromUserPhone;
    }

    public void setFromUserPhone(String fromUserPhone) {
        this.fromUserPhone = fromUserPhone;
    }

    public String getToUserPhone() {
        return toUserPhone;
    }

    public void setToUserPhone(String toUserPhone) {
        this.toUserPhone = toUserPhone;
    }
}
