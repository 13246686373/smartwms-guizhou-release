package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: dizhang
 * @Date: 2018/7/27
 * @Desc:
 **/
@Data
public class ShipmentManageListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id ;

    private String singleNumber;

    private String clientName;

    private String wareHouseName;

    private String pickTypeName;

    private Date deliveryTime;

    private Integer status;

    private String statusName;

    @Override
    public String toString() {
        return "ShipmentManageListVO{" +
                "id=" + id +
                ", singleNumber='" + singleNumber + '\'' +
                ", clientName='" + clientName + '\'' +
                ", wareHouseName='" + wareHouseName + '\'' +
                ", pickTypeName='" + pickTypeName + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
