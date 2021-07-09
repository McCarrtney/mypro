package com.dzqc.cloud.dto;

import java.io.Serializable;
import java.util.Date;

public class TokenInfo implements Serializable {
    private String token;
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
