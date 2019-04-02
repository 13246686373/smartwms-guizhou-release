package com.frdscm.wms.entity.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @Author: dizhang
 * @Date: 2018/8/8
 * @Desc:
 **/
@Data
public class WarehouseVO implements Serializable {

    private Integer id;
    /**
     * 仓库代码
     */
    private String code;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * 仓库类型ID
     */
    private Integer typeId;
    /**
     * 仓库类型名称
     */
    private String typeName;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 所属片区
     */
    private String areaName;
    /**
     * 备注
     */
    private String remark;

    /**
     * 仓库容量
     */
    private BigDecimal capacitySum;

    /**
     * 储位个数
     */
    private Integer warehouseStorageCount;

    private BigDecimal longitude;

    private BigDecimal latitude;
}
