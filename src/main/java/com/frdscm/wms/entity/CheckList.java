package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
@Data
public class CheckList implements Serializable {

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
     * 收货日期
     */
    private Date receiptTime;
    /**
     * 单位对应收出货类型 1-数量 2-箱数 3-板数 4-重量 5-体积
     */
    private Integer unitType;
    /**
     * 状态 -1-删除 0-不良品 1-良品
     */
    private Integer goodsStatus;
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
     * 盘点管理ID
     */
    private Integer checkManageId;
    /**
     * 盘点结果 1-正常 2-数量异常 3-破损异常
     */
    private Integer checkResult;
    /**
     * 盘点数量
     */
    private BigDecimal checkSum;
    /**
     * 收货单号
     */
    private String singleNumber;
    /**
     * 盘点状态 1-未盘点 2-已盘点
     */
    private Integer status;

    @Override
    public String toString() {
        return "CheckList{" +
                "id=" + id +
                ", boardNumber='" + boardNumber + '\'' +
                ", warehouseStorageNumber='" + warehouseStorageNumber + '\'' +
                ", materialNumber='" + materialNumber + '\'' +
                ", materialName='" + materialName + '\'' +
                ", unit='" + unit + '\'' +
                ", quantityCount=" + quantityCount +
                ", boxCount=" + boxCount +
                ", grossWeight=" + grossWeight +
                ", netWeight=" + netWeight +
                ", volume=" + volume +
                ", checkManageId=" + checkManageId +
                ", checkResult=" + checkResult +
                ", checkSum=" + checkSum +
                ", singleNumber='" + singleNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
