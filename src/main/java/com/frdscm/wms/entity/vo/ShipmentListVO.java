package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: dizhang
 * @Date: 2018/8/7
 * @Desc:
 **/
@Data
public class ShipmentListVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 板号
     */
    private String boardNumber;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 物料料号
     */
    private String materialNumber;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 储位
     */
    private String warehouseStorageName;
    /**
     * 箱数
     */
    private Integer boxCount;
    /**
     * 数量
     */
    private Integer quantityCount;
    /**
     * 毛重（KG）
     */
    private BigDecimal grossWeight;
    /**
     * 净重（KG）
     */
    private BigDecimal netWeight;
    /**
     * 体积（m³）
     */
    private BigDecimal volume;
    /**
     * 出货管理ID
     */
    private Integer shipmentManageId;

    /**
     * 实际出货数量
     */
    private Integer shipmentActualCount;

    /**
     * 状态 1-未扫描 2-已扫描
     */
    private Integer status;

    private  Integer unitId;

    private String unit;

}
