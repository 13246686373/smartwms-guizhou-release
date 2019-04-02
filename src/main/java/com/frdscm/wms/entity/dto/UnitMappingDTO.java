package com.frdscm.wms.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: dizhang
 * @Date: 2018/8/15
 * @Desc:
 **/
@Data
public class UnitMappingDTO {
    /**
     * 是否箱数
     */
    private Integer isBoxCount;
    /**
     * 是否数量
     */
    private Integer isQuantityCount;
    /**
     * 是否毛重（KG）
     */
    private Integer isGrossWeight;
    /**
     * 是否净重（KG）
     */
    private Integer isNetWeight;
    /**
     * 是否计体积收货
     */
    private Integer isVolumeReceipt;
    /**
     * 是否按板数收货
     */
    private Integer isBoard;
    /**
     * 单个重量
     */
    private BigDecimal singleWeight;
    /**
     * 单个体积
     */
    private BigDecimal singleVolume;
    /**
     * 箱数/板
     */
    private Integer boxCount;
    /**
     * 数量/箱
     */
    private Integer quantity;
    /**
     * 毛重/箱
     */
    private BigDecimal grossWeight;
}
