package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出货需求表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class ShipmentDemand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 出货单号
     */
    private String singleNumber;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 出货仓库ID
     */
    private Integer warehouseId;
    /**
     * 出货仓库名称
     */
    private String warehouseName;
    /**
     * 出货类型ID
     */
    private Integer typeId;
    /**
     * 出货类型名称
     */
    private String typeName;
    /**
     * 需求来源ID
     */
    private Integer sourceId;
    /**
     * 需求来源名称
     */
    private String sourceName;
    /**
     * 来源单号
     */
    private String sourceNumber;
    /**
     * 交货日期
     */
    private Date deliveryTime;
    /**
     * 拣货要求ID
     */
    private Integer pickTypeId;
    /**
     * 拣货要求名称
     */
    private String pickTypeName;
    /**
     * 出货方式ID
     */
    private Integer shipmentMethodId;
    /**
     * 出货方式名称
     */
    private String shipmentMethodName;
//    /**
//     * 车牌ID
//     */
//    private Integer carId;
//    /**
//     * 车牌名称
//     */
//    private String carName;
//    /**
//     * 司机ID
//     */
//    private Integer driverId;
//    /**
//     * 司机姓名
//     */
//    private String driverName;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 收货方ID
     */
    private Integer cargoPartyId;
    /**
     * 收货方(代码)
     */
    private String cargoPartyName;
    /**
     * 收货方Id
     */
    private Integer consigneeId;
    /**
     * 收货方
     */
    private String consigneeName;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 收货地址
     */
    private String receiptAddress;
    /**
     * 所属片区
     */
    private String areaName;
    /**
     * 备注
     */
    private String remark;
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
     * 操作人ID
     */
    private Integer operatorId;
    /**
     * 操作人
     */
    private String operatorName;
    /**
     * 状态 -1-删除 1-未确认 2-已确认
     */
    private Integer status;
    /**
     * 关联项目ID
     */
    private Integer projectId;
    /**
     * 关联项目名称
     */
    private String projectName;

    @Override
    public String toString() {
        return "ShipmentDemand{" +
                "id=" + id +
                ", singleNumber='" + singleNumber + '\'' +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", warehouseId=" + warehouseId +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", sourceId=" + sourceId +
                ", sourceName='" + sourceName + '\'' +
                ", sourceNumber='" + sourceNumber + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", pickTypeId=" + pickTypeId +
                ", pickTypeName='" + pickTypeName + '\'' +
                ", shipmentMethodId=" + shipmentMethodId +
                ", shipmentMethodName='" + shipmentMethodName + '\'' +
                ", phone='" + phone + '\'' +
                ", receiptAddress='" + receiptAddress + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                ", status=" + status +
                '}';
    }
}
