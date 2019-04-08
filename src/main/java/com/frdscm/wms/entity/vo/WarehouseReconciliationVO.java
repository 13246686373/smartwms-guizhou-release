package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author dizhang
 * @since 2019-04-06
 */
@Data
public class WarehouseReconciliationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 合同ID
     */
    private Integer orderId;
    /**
     * 合同编号
     */
    private String orderNo;
    /**
     * 对账单号
     */
    private String reconciliationNo;
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
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 对账单状态 0-未确认 1-对账单确认  2-财务确认
     */
    private Integer status;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 货币例如 RMB
     */
    private String currency;
    /**
     * 订单费用项总计
     */
    private BigDecimal orderExpenseItemTotal;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否有效: Y有效  N失效
     */
    private String enabledFlag;
}
