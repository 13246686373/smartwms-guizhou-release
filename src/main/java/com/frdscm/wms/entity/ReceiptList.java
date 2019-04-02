package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收货列表
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Data
public class ReceiptList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 收货模式 1-板进 2-箱进 3-PCS进
     */
    private Integer receiptMode;
    /**
     * 收货管理ID
     */
    private Integer receiptManageId;
    /**
     * 状态 1-未上架 2-已上架 -1-已删除
     */
    private Integer status;
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
    /**
     * 拣货总量
     */
    private BigDecimal shouldPickCount;
    /**
     * 是否收货扫描
     */
    private Integer isReceiptScan;
    /**
     * 是否出货扫描
     */
    private Integer isShipmentScan;
    /**
     * 扫描是否完成
     */
    private Integer scanned;
}
