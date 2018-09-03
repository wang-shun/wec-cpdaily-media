package com.wisedu.wec.media.common.to;

/**
 * Created by zsl on 2018/4/20.
 */
public class SendMessageResultBean<T> {
    private String errCode;
    private String errMsg;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
