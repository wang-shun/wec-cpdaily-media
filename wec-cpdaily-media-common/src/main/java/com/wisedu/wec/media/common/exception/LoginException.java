package com.wisedu.wec.media.common.exception;

/**
 * Created by dongjunqing on 2018/1/3.
 */
public class LoginException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1287895988201119701L;

    public LoginException() {
        super();
    }

    public LoginException(String s) {
        super(s);
    }

    public LoginException(Throwable e) {
        super(e);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
