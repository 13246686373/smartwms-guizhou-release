package com.frdscm.wms.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单费用项表
 *
 * @author zhangling
 * @since 2019-04-05
 */
@Data
@ApiModel("订单费用项对象")
public class ExpenseItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "费用项主键ID")
    private Integer id;

    @ApiModelProperty(value = "订单ID")
    private Integer orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "订单费用项名称", required = true)
    private String expenseItemName;

    @ApiModelProperty(value = "订单关键数据", required = true)
    private String orderKeyData;

    @ApiModelProperty(value = "单价", required = true)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "费用项价格总计")
    private BigDecimal expenseItemTotal;
}
