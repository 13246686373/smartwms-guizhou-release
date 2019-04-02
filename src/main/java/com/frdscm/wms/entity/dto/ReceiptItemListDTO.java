package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: dizhang
 * @Date: 2018/7/6
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("收货明细Item")
public class ReceiptItemListDTO implements Serializable {

    @ApiModelProperty(value = "添加不传，修改必传")
    private Integer id;

    @ApiModelProperty(value = "批次号", required = true)
    private String batchNumber;

    @ApiModelProperty(value = "物料料号", required = true)
    @NotBlank(message = "物料料号不能为空")
    @Length(min = 1, max = 256, message = "物料料号长度异常")
    private String materialNumber;

    @ApiModelProperty(value = "物料名称", required = true)
    @NotBlank(message = "物料名称不能为空")
    @Length(min = 1, max = 256, message = "物品名称长度异常")
    private String materialName;

    private String materialSpecifications;

    @ApiModelProperty(value = "收货箱数", required = true)
    private Integer boxCount;

    @ApiModelProperty(value = "收货数量", required = true)
    private Integer quantityCount;

    @ApiModelProperty(value = "收货毛重（KG）", required = true)
    private BigDecimal grossWeight;

    @ApiModelProperty(value = "收货净重（KG）", required = true)
    private BigDecimal netWeight;

    @ApiModelProperty(value = "收货体积（m³）", required = true)
    private BigDecimal volume;

    @ApiModelProperty(value = "收货板数", required = true)
    private Integer boardCount;

    @ApiModelProperty(value = "单位", required = true)
    private String unit;

    @ApiModelProperty(value = "单位ID", required = true)
    private Integer unitId;

    @ApiModelProperty(value = "收货列表ID")
    private Integer receiptListId;
}
