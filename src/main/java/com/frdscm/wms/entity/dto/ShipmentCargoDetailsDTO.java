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
 * @Date: 2018/7/11
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("出货明细")
public class ShipmentCargoDetailsDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收货明细ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "物料料号", required = true)
    @NotBlank(message = "物料料号不能为空")
    @Length(min = 1, max = 32, message = "物料料号长度异常")
    private String materialNumber;

    @ApiModelProperty(value = "物料名称", required = true)
    @NotBlank(message = "物料名称不能为空")
    @Length(min = 1, max = 255, message = "物料名称长度异常")
    private String materialName;

    @ApiModelProperty(value = "物料规格", required = true)
    @NotBlank(message = "物料规格不能为空")
    @Length(min = 1, max = 255, message = "物料规格长度异常")
    private String materialSpecifications;

    @ApiModelProperty(value = "批次号", required = true)
    @NotBlank(message = "批次号不能为空")
    @Length(min = 1, max = 32, message = "批次号长度异常")
    private String batchNumber;

    @ApiModelProperty(value = "板数", required = true)
    private Integer boardCount;

    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private Integer quantityCount;

    @ApiModelProperty(value = "箱数", required = true)
    private Integer boxCount;

    @ApiModelProperty(value = "毛重", required = true)
    private BigDecimal grossWeight;

    @ApiModelProperty(value = "净重", required = true)
    private BigDecimal netWeight;

    @ApiModelProperty(value = "净重", required = true)
    private BigDecimal volume;

    @ApiModelProperty(value = "单位ID" ,required = true)
    @NotNull(message="单位ID不能为空")
    private Integer unitId;

    @ApiModelProperty(value = "单位", required = true)
    @NotBlank(message = "单位不能为空")
    @Length(min = 1, max = 32, message = "单位长度异常")
    private String unit;

    @ApiModelProperty(value = "单位对应收出货类型 1-数量 2-箱数 3-板数 4-重量 5-体积", required = true)
    @NotBlank(message = "单位对应收出货类型不能为空")
    private Integer unitType;

    @ApiModelProperty(value = "出货需求ID", required = true)
    @NotNull(message = "出货需求ID不能为空")
    private Integer shipmentDemandId;
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

    private BigDecimal shouldPickCount;
}
