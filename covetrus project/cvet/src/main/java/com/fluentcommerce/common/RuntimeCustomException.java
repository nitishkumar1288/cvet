package com.fluentcommerce.common;

/**
 * @author Nandhakumar
 */
public class RuntimeCustomException extends Exception {
    static final long serialVersionUID = -7034897190745766939L;

    public RuntimeCustomException() {
        super();
    }

    public RuntimeCustomException(String message) {
        super(message);
    }

    public RuntimeCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeCustomException(Throwable cause) {
        super(cause);
    }

    protected RuntimeCustomException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
