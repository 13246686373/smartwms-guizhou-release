package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存管理表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class InventoryListVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 板号
     */
    private String boardNumber;
    /**
     * 储位号
     */
    private String warehouseStorageNumber;
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
     * 物料规格
     */
    private String materialSpecifications;
    /**
     * 收货箱数
     */
    private Integer boxCount;
    /**
     * 收货数量
     */
    private Integer quantityCount;
    /**
     * 收货毛重（KG）
     */
    private BigDecimal grossWeight;
    /**
     * 收货净重（KG）
     */
    private BigDecimal netWeight;
    /**
     * 收货体积（m³）
     */
    private BigDecimal volume;
    /**
     * 收货板数
     */
    private Integer boardCount;
    /**
     * 收货日期
     */
    private Date receiptTime;
    /**
     * 状态 -1-删除 0-不良品 1-良品
     */
    private Integer goodsStatus;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单位ID
     */
    private Integer unitId;
    /**
     * 单位对应收出货类型 1-数量 2-箱数 3-板数 4-重量 5-体积
     */
    private Integer unitType;
    /**
     * 数量/箱
     */
    private Integer quantityScale;
    /**
     * 箱数/板
     */
    private Integer boxScale;
    /**
     * 单位重量
     */
    private BigDecimal weightScale;
    /**
     * 单位体积
     */
    private BigDecimal volumeScale;
    /**
     * 单位体积单位
     */
    private Integer perUnitVolume;
    /**
     * 单位重量单位
     */
    private Integer perUnitWeight;
}
