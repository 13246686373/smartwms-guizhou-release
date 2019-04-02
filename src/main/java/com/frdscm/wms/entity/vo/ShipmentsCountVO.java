package com.frdscm.wms.entity.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/3
 * @Desc:
 **/
@Data
public class ShipmentsCountVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户ID
     */
    private String clientId;

    /**
     * 待确认
     */
    private Integer toBeConfirmed;

    /**
     * 待拣货
     */
    private Integer toBeShipments;

    /**
     * 拣货中
     */
    private Integer inShipmentsOfGoods;

    /**
     * 待出货
     */
    private Integer toBeShipped;

    @Override
    public String toString() {
        return "ShipmentsCountVO{" +
                "clientName='" + clientName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", toBeConfirmed=" + toBeConfirmed +
                ", toBeShipments=" + toBeShipments +
                ", inShipmentsOfGoods=" + inShipmentsOfGoods +
                ", toBeShipped=" + toBeShipped +
                '}';
    }
}
