package com.frdscm.wms.entity.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
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
@ApiModel("应收对账单对象")
public class WarehouseReconciliationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "订单ID")
    private Integer orderId;

    @ApiModelProperty(value = "对账单编号")
    private String reconciliationNo;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "客户ID")
    private Integer clientId;

    @ApiModelProperty(value = "客户名称")
    private String clientName;

    @ApiModelProperty(value = "合同编号")
    private String contractNo;

    @ApiModelProperty(value = "仓库ID")
    private Integer warehouseId;

    @ApiModelProperty(value = "对账单状态 0-未确认 1-对账单确认  2-财务确认")
    private Integer status;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "货币")
    private String currency;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否有效: Y有效  N失效")
    private String enabledFlag;

}
