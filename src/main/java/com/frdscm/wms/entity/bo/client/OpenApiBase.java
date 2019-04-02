package com.frdscm.wms.entity.bo.client;

import lombok.Data;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/10/29 7:25 PM
 */
@Data
public class OpenApiBase<T> {
    private T data;
    private String sign;
}
