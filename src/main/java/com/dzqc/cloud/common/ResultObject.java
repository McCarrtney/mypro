package com.dzqc.cloud.common;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultObject implements Serializable {

	private static final long serialVersionUID = 1L;

    private Integer retCode;
	private String msg;
    private Boolean suc;
    private Object result;

    public ResultObject(String msg, Boolean suc, Object result, int retCode) {
        this.retCode = retCode;
        this.msg = msg;
        this.suc = suc;
        this.result = result;
    }
    
    public static ResultObject success() {
    	return new ResultObject( "ok", true, null, 200);
    }
    
    public static ResultObject success(Object result) {
    	return new ResultObject("ok", true, result, 200);
    }

    public static ResultObject error(String msg) {
        return new ResultObject(msg, false, null, 0);
    }

    public static ResultObject error(String msg , Object result) {
        return new ResultObject(msg, false, result, 0);
    }
    public static ResultObject error(String msg, Integer ecode) {
    	return new ResultObject(msg, false, null, ecode);
    }
    
    public static ResultObject error(String msg , Object result, Integer ecode) {
    	return new ResultObject(msg, false, result, ecode);
    }
    

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuc() {
        return suc;
    }

    public void setSuc(Boolean suc) {
        this.suc = suc;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }
}

/*
* error code list
* code description
* 200  success
* 900  reserve
* 901  登录过期或未登录
* 902  注册时号码已注册
* 903  注册失败
* 904  号码未注册
* 905  该用户无健康记录
* 906  没找到医生
* 907  没找到病历
* 908  无访问权限*/
