package com.frdscm.wms.commons.exceptions;


import com.frdscm.wms.commons.enums.ResponseEnum;

/**
 * @author: March_CD
 * @description: 业务异常
 */
public class BusinessException extends RuntimeException {

    private ResponseEnum responseEnum;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}
