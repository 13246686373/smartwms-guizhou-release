package com.frdscm.wms.commons.exceptions;


/**
 * @author: March_CD
 * @description: 业务异常
 */
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
