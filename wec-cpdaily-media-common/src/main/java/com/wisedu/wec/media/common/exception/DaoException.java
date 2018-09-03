package com.wisedu.wec.media.common.exception;

/**
 * Created by huhaichao on 2017/5/11.
 */
public class DaoException extends BusinessException {

    /**
     *
     */
    private static final long serialVersionUID = 3288895988701119701L;

    public DaoException() {
        super();
    }

    public DaoException(String s) {
        super(s);
    }

    public DaoException(Throwable e) {
        super(e);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }


}
