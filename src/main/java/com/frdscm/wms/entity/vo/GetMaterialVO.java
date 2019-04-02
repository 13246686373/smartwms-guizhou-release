package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: dizhang
 * @Date: 2018/8/4
 * @Desc:
 **/
@Data
public class GetMaterialVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String materialNumber;

    private String materialName;

    private String batchNumber;

    private String materialSpecifications;

    private Integer quantityCountSum;

    private String unit;

    private Integer unitId;

    private Integer unitType;

    private Integer quantityScale;

    private Integer boardCountSum;

    private Integer boxScale;

    private BigDecimal weightScale;

    private BigDecimal volumeScale;

    private Integer perUnitVolume;

    private Integer perUnitWeight;

    private Integer boxCountSum;

    private BigDecimal grossWeightSum;

    private BigDecimal netWeightSum;

    private BigDecimal volumeSum;

    private Integer isReceiptScan;

    private Integer isShipmentScan;

    @Override
    public String toString() {
        return "GetMaterialVO{" +
                "id=" + id +
                ", materialNumber='" + materialNumber + '\'' +
                ", materialName='" + materialName + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", materialSpecifications='" + materialSpecifications + '\'' +
                ", quantityCountSum=" + quantityCountSum +
                ", unit='" + unit + '\'' +
                ", unitId=" + unitId +
                ", boxCountSum=" + boxCountSum +
                ", grossWeightSum=" + grossWeightSum +
                ", netWeightSum=" + netWeightSum +
                ", volumeSum=" + volumeSum +
                '}';
    }
}
