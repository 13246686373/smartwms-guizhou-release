package com.frdscm.wms.entity.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: dizhang
 * @Date: 2018/8/2
 * @Desc:
 **/
@Data
public class CheckManageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String checkNumber;

    private String warehouseName;

    private Integer warehouseId;

    private Integer checkTypeId;

    private String checkTypeName;

    private Integer clientId;

    private String clientName;

    private String materialNumber;

    private String materialName;

    private Date receiptTimeStart;

    private Date receiptTimeEnd;

    private Integer status;

    private String filePath;

    private String fileName;

    @Override
    public String toString() {
        return "CheckManageVO{" +
                "id=" + id +
                ", checkNumber='" + checkNumber + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseId=" + warehouseId +
                ", checkTypeId=" + checkTypeId +
                ", checkTypeName='" + checkTypeName + '\'' +
                ", status=" + status +
                '}';
    }
}
