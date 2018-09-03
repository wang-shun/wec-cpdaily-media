package com.wisedu.wec.media.common.old.vo;

/**
 * Created by croyson on 2017/10/23.
 */
public class ApiResp{


    private int errCode;

    private String errMsg;

    private OssStsInfo data;

    public OssStsInfo getData() {
        return data;
    }

    public void setData(OssStsInfo data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


}
