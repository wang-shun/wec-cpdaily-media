package com.wisedu.wec.media.common.old.exception;

import org.springframework.http.HttpStatus;

import com.wisedu.wec.media.common.old.constants.BaseErrTypeE;

public class ServiceException extends AbstractMediaPlatformHttpException {

  public ServiceException(String msg, String code) {
    super(msg);
    this.code = code;
    setHttpStatus(HttpStatus.OK);
  }

  public ServiceException(BaseErrTypeE e) {
    super(e.getDesc());
    this.code =String.valueOf(e.getnCode());
    setHttpStatus(HttpStatus.OK);
  }



  public ServiceException(String msg, Exception e) {
    super(msg, e);
    setHttpStatus(HttpStatus.OK);
  }
}
