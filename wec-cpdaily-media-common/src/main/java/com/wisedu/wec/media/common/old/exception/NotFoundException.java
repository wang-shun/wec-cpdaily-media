package com.wisedu.wec.media.common.old.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractMediaPlatformHttpException {

  public NotFoundException(String msg, String code) {
    super(msg);
    setCode(code);
    setHttpStatus(HttpStatus.OK);
  }

}
