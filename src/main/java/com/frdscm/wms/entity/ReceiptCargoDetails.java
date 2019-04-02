package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收货需求货物明细表
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Data
public class ReceiptCargoDetails implements Serializable {

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
     * 板数
     */
    private Integer boardCount;
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
     * 收货需求ID
     */
    private Integer receiptDemandId;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单位对应收出货类型 1-数量 2-箱数 3-板数 4-重量 5-体积
     */
    private Integer unitType;
    /**
     * 单位ID
     */
    private Integer unitId;
    /**
     * 实际收货箱数
     */
    private Integer actualBoxCount;
    /**
     * 实际收货数量
     */
    private Integer actualQuantityCount;
    /**
     * 实际收货毛重（KG）
     */
    private BigDecimal actualGrossWeight;
    /**
     * 实际收货净重（KG）
     */
    private BigDecimal actualNetWeight;
    /**
     * 实际收货 体积（m³）
     */
    private BigDecimal actualVolume;
    /**
     * 实际收货板数
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
    /**
     * 是否出货扫描
     */
    private Integer isReceiptScan;
    /**
     * 是否出货扫描
     */
    private Integer isShipmentScan;

    @Override
    public String toString() {
        return "ReceiptCargoDetails{" +
                "id=" + id +
                ", materialNumber='" + materialNumber + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialSpecifications='" + materialSpecifications + '\'' +
                ", boardCount=" + boardCount +
                ", boxCount=" + boxCount +
                ", quantityCount=" + quantityCount +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", volume=" + volume +
                ", receiptDemandId=" + receiptDemandId +
                ", batchNumber='" + batchNumber + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
