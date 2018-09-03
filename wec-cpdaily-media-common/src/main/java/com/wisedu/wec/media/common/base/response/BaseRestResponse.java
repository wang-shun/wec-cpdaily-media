package com.wisedu.wec.media.common.base.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class BaseRestResponse implements Serializable {

    private String code = "0";
    private String message = "请求成功";

    public BaseRestResponse(){}
    public BaseRestResponse(String code,String message){
        this.code=code;
        this.message=message;
    }

    public BaseRestResponse success(String message) {
        this.code = "0";
        this.message = message;
        return this;
    }

    public BaseRestResponse error(String message) {
        this.code = HttpStatus.BAD_REQUEST.toString();
        this.message = message;
        return this;
    }

    //兼容老版本begin
    public String getErrCode() {
        return code;
    }

    public String getErrMsg() {
        return message;
    }
    //兼容老版本end
}
