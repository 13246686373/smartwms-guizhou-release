package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/3
 * @Desc:
 **/
@Data
public class ReceiptCountVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户名称
     */
    private  String clientName;

    /**
     * 客户ID
     */
    private String clientId;

    /**
     * 待确认
     */
    private Integer toBeConfirmed;

    /**
     * 待收货
     */
    private  Integer toBeReceived;

    /**
     * 收货中
     */
    private Integer inReceiptOfGoods;

    /**
     * 待入储
     */
    private Integer toBePendingStorage;

    @Override
    public String toString() {
        return "ReceiptCountVO{" +
                "clientName='" + clientName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", toBeConfirmed=" + toBeConfirmed +
                ", toBeReceived=" + toBeReceived +
                ", inReceiptOfGoods=" + inReceiptOfGoods +
                ", toBePendingStorage=" + toBePendingStorage +
                '}';
    }
}
