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
@ApiModel("收货明细")
public class ReceiptListDTO implements Serializable {

    @ApiModelProperty(value = "收货明细列表ID 添加操作是不传，修改操作必传")
    private Integer id;

    @ApiModelProperty(value = "储位号", required = true)
    private String warehouseStorageNumber;

    @ApiModelProperty(value = "板号", required = true)
    @NotBlank(message = "板号不能为空")
    @Length(min = 1, max = 256, message = "板号长度异常")
    private String boardNumber;

    @ApiModelProperty(value = "收货管理ID", required = true)
    @NotNull(message = "收货管理ID不能为空")
    private Integer receiptManageId;

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
    @NotBlank(message = "单位不能为空")
    private String unit;

    @ApiModelProperty(value = "单位ID", required = true)
    @NotNull(message = "单位ID不能为空")
    private Integer unitId;

    @ApiModelProperty(value = "单位对应收出货类型 1-数量 2-箱数 3-板数 4-重量 5-体积", required = true)
    @NotNull(message = "单位对应收出货类型不能为空")
    private Integer unitType;
    /**
     * 数量/箱
     */
    private Integer quantityScale;
    /**
     * 箱数/板
     */
    private Integer boxScale;
    /**
     * 单位重量
     */
    private BigDecimal weightScale;
    /**
     * 单位体积
     */
    private BigDecimal volumeScale;
    /**
     * 单位体积单位
     */
    private Integer perUnitVolume;
    /**
     * 单位重量单位
     */
    private Integer perUnitWeight;

    private Integer isReceiptScan;

    private Integer isShipmentScan;

    @ApiModelProperty(value = "单位对应收出货类型 1-数量 2-箱数 3-板数 4-重量 5-体积", required = true)
    @NotNull(message = "拣货总量不能为空")
    private BigDecimal shouldPickCount;
}
