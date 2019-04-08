package com.frdscm.wms.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库订单表
 *
 * @author zhangling
 * @since 2019-04-05
 */
@Data
public class WarehouseOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Date contactStartTime;
    /**
     * 合同结束时间
     */
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
     * 操作人ID
     */
    private Integer operatorId;
    /**
     * 操作人名称
     */
    private String operatorName;
    /**
     * 是否有效: Y 有效 N失效
     */
    private String enabledFlag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
