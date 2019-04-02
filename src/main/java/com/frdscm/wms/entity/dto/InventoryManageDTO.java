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
import java.util.Date;

/**
 * @Author: dizhang
 * @Date: 2018/7/9
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("库存")
public class InventoryManageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库存ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "板号", required = true)
    @NotBlank(message = "板号不能为空")
    @Length(min = 1, max = 256, message = "板号名称长度异常")
    private String boardNumber;

    @ApiModelProperty(value = "储位号", required = true)
    private String warehouseStorageNumber;

    @ApiModelProperty(value = "批次号", required = true)
    private String batchNumber;

    @ApiModelProperty(value = "仓库ID", required = true)
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;


    @ApiModelProperty(value = "客户ID", required = true)
    @NotNull(message = "客户ID不能为空")
    private Integer clientId;

    @ApiModelProperty(value = "客户名称", required = true)
    @NotBlank(message = "客户名称不能为空")
    private String clientName;

    @ApiModelProperty(value = "收货单号", required = true)
    @NotBlank(message = "收货单号不能为空")
    private String singleNumber;

    @ApiModelProperty(value = "物料料号", required = true)
    @NotBlank(message = "物料料号不能为空")
    private String materialNumber;

    @ApiModelProperty(value = "物料名称", required = true)
    @NotBlank(message = "物料名称不能为空")
    private String materialName;

    @ApiModelProperty(value = "物料规格", required = true)
    private String materialSpecifications;

    @ApiModelProperty(value = "数量", required = true)
    private Integer quantityCount;

    @ApiModelProperty(value = "箱数", required = true)
    private Integer boxCount;

    @ApiModelProperty(value = "毛重（KG）", required = true)
    private BigDecimal grossWeight;

    @ApiModelProperty(value = "净重（KG）", required = true)
    private BigDecimal netWeight;

    @ApiModelProperty(value = "体积（m³）", required = true)
    private BigDecimal volume;

    @ApiModelProperty(value = "单位", required = true)
    private String unit;

    @ApiModelProperty(value = "单位ID", required = true)
    private Integer unitId;

    @ApiModelProperty(value = "收货日期", required = true)
    private Date receiptTime;

    @ApiModelProperty(value = "备注", required = true)
    private String remark;


}
