package com.wisedu.wec.media.common.old.exception;


import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractMediaPlatformHttpException {

  public BadRequestException(String msg, String code) {
    super(msg);
    setCode(code);
    setHttpStatus(HttpStatus.OK);
  }
}
