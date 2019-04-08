package com.frdscm.wms.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单货物明细表
 *
 * @author zhangling
 * @since 2019-04-05
 */
@Data
@ApiModel("订单货物明细对象")
public class CargoDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单货物明细主键ID")
    private Integer id;

    @ApiModelProperty(value = "订单ID")
    private Integer orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "物料名称")
    private String materialsName;

    @ApiModelProperty(value = "产品规格")
    private String specificationGrade;

    @ApiModelProperty(value = "批次号")
    private String batchNo;

    @ApiModelProperty(value = "单位Id")
    private Integer unitId;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "数量")
    private BigDecimal quantity;

    @ApiModelProperty(value = "体积")
    private BigDecimal volume;

}
