package com.frdscm.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 仓库订单表
 *
 * @author zhangling
 * @since 2019-04-05
 */
@Data
public class WarehouseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 合同开始时间
     */
    @JSONField(serialize = false)
    @TableField(update = "now()")
    private Date contactStartTime;
    /**
     * 合同结束时间
     */
    @JSONField(serialize = false)
    @TableField(update = "now()")
    private Date contactEndTime;
    /**
     * 收货仓库ID
     */
    private Integer warehouseId;
    /**
     * 收货仓库
     */
    private String receivingWarehouse;
    /**
     * 预计到仓时间
     */
    @JSONField(serialize = false)
    @TableField(update = "now()")
    private Date rojectedToWarehouseTime;
    /**
     * 合同附件服务地址
     */
    private String addressOfContractAnnex;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效: Y 有效 N失效
     */
    private String enabledFlag;
    /**
     * 订单模式: 1-普通入库  2-仓单质押  3-电商下单
     */
    private Integer orderType;
    /**
     * 操作人ID
     */
    private Integer operatorId;
    /**
     * 操作人名称
     */
    private String operatorName;
    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    private Date createTime;
    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @TableField(update = "now()")
    private Date updateTime;

    @Override
    public String toString() {
        return "WarehouseOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo +
                ", clientId='" + clientId +
                ", clientName='" + clientName +
                ", contract_no='" + contractNo +
                ", contactStartTime=" + contactStartTime +
                ", contactEndTime=" + contactEndTime +
                ", warehouseId='" + warehouseId +
                ", receivingWarehouse='" + receivingWarehouse +
                ", rojectedToWarehouseTime=" + rojectedToWarehouseTime +
                ", addressOfContractAnnex='" + addressOfContractAnnex +
                ", remark='" + remark +
                ", orderType='" + orderType +
                ", operatorId='" + operatorId +
                ", operatorName='" + operatorName +
                ", enabledFlag='" + enabledFlag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
