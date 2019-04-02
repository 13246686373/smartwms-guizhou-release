package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 拣货列表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class ShipmentList implements Serializable {

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
     * 出货管理ID
     */
    private Integer shipmentManageId;
    /**
     * 是否拆板
     */
    private Integer isBaffle;
    /**
     * 仓库管理ID
     */
    private Integer inventoryManageId;
    /**
     * 状态 1-未扫描 2-已扫描 -1-已删除
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
     * 板数
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

    @Override
    public String toString() {
        return "ShipmentList{" +
                ", id=" + id +
                ", boardNumber=" + boardNumber +
                ", shipmentManageId=" + shipmentManageId +
                ", status=" + status +
                "}";
    }
}
