package com.wisedu.wec.media.web.controller;

/**
 * Created by zsl on 2018/7/9.
 */
public class MediaLoginException extends Exception {

    private String errorCode;

    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}