package com.wisedu.wec.media.common.old.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractMediaPlatformHttpException extends RuntimeException {

  private static final long serialVersionUID = -8504985666432269721L;

  protected HttpStatus httpStatus;

  protected String code;

  public AbstractMediaPlatformHttpException(String msg) {
    super(msg);
  }

  public AbstractMediaPlatformHttpException(String msg, Exception e) {
    super(msg, e);
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  protected void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getCode() {
    return code;
  }

  protected void setCode(String code) {
    this.code = code;
  }

}
