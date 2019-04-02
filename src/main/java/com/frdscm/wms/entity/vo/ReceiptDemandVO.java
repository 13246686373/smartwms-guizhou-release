package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 收货需求表
 *
 * @author dizhang
 * @since 2018-07-03
 */
@Data
public class ReceiptDemandVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 收货单号
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
     * 收货仓库ID
     */
    private Integer warehouseId;

    /**
     * 收货仓库
     */
    private String warehouseName;
    /**
     * 收货类型ID
     */
    private Integer typeId;
    /**
     * 收货类型名称
     */
    private String typeName;
    /**
     * 收货类型ID
     */
    private Integer modeId;
    /**
     * 收货类型名称
     */
    private String modeName;
    /**
     * 关联项目ID
     */
    private Integer projectId;
    /**
     * 关联项目名称
     */
    private String projectName;
    /**
     * 需求来源ID
     */
    private Integer sourceId;
    /**
     * 需求来源名称
     */
    private String sourceName;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 运单号(多个用逗号分隔)
     */
    private String waybillNumber;
    /**
     * 预计到仓时间
     */
    private Date expectedTime;
    /**
     * 操作模式ID
     */
    private Integer operatingModeId;
    /**
     * 操作模式名称
     */
    private String operatingModeName;
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
     * 状态名称
     */
    private String statusName;

    /**
     * 仓库详细信息
     */
    private List<ReceiptCargoDetails> receiptCargoDetailsList;

    @Override
    public String toString() {
        return "ReceiptDemandVO{" +
                "id=" + id +
                ", singleNumber='" + singleNumber + '\'' +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", modeId=" + modeId +
                ", modeName='" + modeName + '\'' +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", sourceId=" + sourceId +
                ", sourceName='" + sourceName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", waybillNumber='" + waybillNumber + '\'' +
                ", expectedTime=" + expectedTime +
                ", operatingModeId=" + operatingModeId +
                ", operatingModeName='" + operatingModeName + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", receiptCargoDetailsList=" + receiptCargoDetailsList +
                '}';
    }
}
