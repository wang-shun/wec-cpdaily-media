package com.wisedu.wec.media.common.exception;

/**
 * Created by huhaichao on 2017/6/20.
 */
public class ValidateException extends BusinessException {

    public ValidateException() {
        super();
    }

    public ValidateException(String s) {
        super(s);
    }

    public ValidateException(Throwable e) {
        super(e);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

}
