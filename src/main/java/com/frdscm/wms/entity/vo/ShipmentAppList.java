package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拣货列表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class ShipmentAppList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 储位ID
     */
    private Integer warehouseStorageId;
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
     * 已出货数量
     */
    private BigDecimal count;
    /**
     * 状态 1-未扫描 2-已扫描
     */
    private Integer status;

    private Integer unitId;

    private String unit;

    private String warehouseStorageNumber;

    @Override
    public String toString() {
        return "ShipmentAppList{" +
                "id=" + id +
                ", boardNumber='" + boardNumber + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", materialNumber='" + materialNumber + '\'' +
                ", materialName='" + materialName + '\'' +
                ", warehouseStorageId=" + warehouseStorageId +
                ", boxCount=" + boxCount +
                ", quantityCount=" + quantityCount +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", volume=" + volume +
                ", shipmentManageId=" + shipmentManageId +
                ", status=" + status +
                ", unitId=" + unitId +
                ", unit='" + unit + '\'' +
                ", warehouseStorageNumber='" + warehouseStorageNumber + '\'' +
                '}';
    }
}
