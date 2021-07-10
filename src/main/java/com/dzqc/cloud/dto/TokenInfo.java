package com.dzqc.cloud.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class TokenInfo implements Serializable {
    private String token;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expire;

    private static final long serialVersionUID = 1L;

    public TokenInfo(String token, Date expire) {
        this.token = token;
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
