package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出货需求货物明细表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class ShipmentCargoDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 批次号
     */
    private String batchNumber;
    /**
     * 单位ID
     */
    private Integer unitId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单位对应收出货类型 1-数量 2-箱数 3-板数 4-重量 5-体积
     */
    private Integer unitType;
    /**
     * 出货需求ID
     */
    private Integer shipmentDemandId;
    /**
     * 板数
     */
    private Integer boardCount;
    /**
     * 数量
     */
    private Integer quantityCount;
    /**
     * 箱数
     */
    private Integer boxCount;
    /**
     * 毛重
     */
    private BigDecimal grossWeight;
    /**
     * 净重
     */
    private BigDecimal netWeight;
    /**
     * 净重
     */
    private BigDecimal volume;
    /**
     * 已拣数量
     */
    private Integer actualQuantityCount;
    /**
     * 已拣箱数
     */
    private Integer actualBoxCount;
    /**
     * 已拣毛重
     */
    private BigDecimal actualGrossWeight;
    /**
     * 已拣净重
     */
    private BigDecimal actualNetWeight;
    /**
     * 已拣体积
     */
    private BigDecimal actualVolume;
    /**
     * 已拣板数
     */
    private Integer actualBoardCount;
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

    private Integer isReceiptScan;

    private Integer isShipmentScan;
    /**
     * 拣货总量
     */
    private BigDecimal shouldPickCount;

    @Override
    public String toString() {
        return "ShipmentCargoDetails{" +
                "id=" + id +
                ", materialNumber='" + materialNumber + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialSpecifications='" + materialSpecifications + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", quantityCount=" + quantityCount +
                ", unitId=" + unitId +
                ", unit='" + unit + '\'' +
                ", shipmentDemandId=" + shipmentDemandId +
                '}';
    }
}
