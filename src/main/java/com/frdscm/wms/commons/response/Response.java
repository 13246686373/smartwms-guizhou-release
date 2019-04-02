package com.frdscm.wms.commons.response;

import com.frdscm.wms.commons.enums.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chengdong
 * @description: 统一返回
 */
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 7352795226774903119L;
    private boolean success = true;
    private int code;
    private String msg;
    private Object data;

    public Response() {
    }

    public Response(ResponseEnum result) {
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    public Response(ResponseEnum result, Object data) {
        this.code = result.getCode();
        this.msg = result.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
