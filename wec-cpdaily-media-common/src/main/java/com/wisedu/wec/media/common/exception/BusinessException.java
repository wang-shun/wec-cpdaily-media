package com.wisedu.wec.media.common.exception;

/**
 * Created by huhaichao on 2017/5/11.
 */
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3288895988701119701L;

    public BusinessException() {
        super();
    }

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }


}
