package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 库存管理表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class InventoryManage implements Serializable {
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
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 仓库名称
     */
    @TableField(exist = false)
    private String warehouseName;
    /**
     * 锁定状态 1-是 0-否
     */
    private Integer lockStatus;
    /**
     * 状态 -1-删除 0-不良品 1-良品
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(update = "now()")
    private Date updateTime;
    /**
     * 是否异动 1-是 0-否
     */
    private Integer abnormal;

    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 收货单号
     */
    private String singleNumber;
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
     * 数量
     */
    private Integer quantityCount;
    /**
     * 箱数
     */
    private Integer boxCount;
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
     * 收货日期
     */
    private Date receiptTime;
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

    @TableField(exist = false)
    private List<InventoryScanList> inventoryScanLists;

    @Override
    public String toString() {
        return "InventoryManage{" +
                "id=" + id +
                ", boardNumber='" + boardNumber + '\'' +
                ", warehouseStorageNumber='" + warehouseStorageNumber + '\'' +
                ", warehouseId=" + warehouseId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
