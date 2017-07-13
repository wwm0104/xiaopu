package com.chinaxiaopu.xiaopuMobi.exception;

import org.apache.shiro.authc.AccountException;

/**
 * Created by liuwei
 * date: 16/10/17
 */
public class NoLoginAuthorException extends AccountException {

    private static final long serialVersionUID = 8918015960590498719L;

    public NoLoginAuthorException(){
        super();
    }
    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param message the reason for the exception
     */
    public NoLoginAuthorException(String message) {
        super(message);
    }

    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public NoLoginAuthorException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public NoLoginAuthorException(String message, Throwable cause) {
        super(message, cause);
    }
}