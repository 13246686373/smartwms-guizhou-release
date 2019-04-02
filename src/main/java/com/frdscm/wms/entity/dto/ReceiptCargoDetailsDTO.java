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
 * @Date: 2018/7/4
 * @Desc:物料对象
 **/
@Setter
@Getter
@ToString
@ApiModel("收货需求物料DTO")
public class ReceiptCargoDetailsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID(编辑时必传)")
    private Integer id;

    @ApiModelProperty(value = "物料料号", required = true)
    @NotBlank(message = "物料料号不能为空")
    @Length(min = 1, max = 32, message = "物料料号长度异常")
    private String materialNumber;

    @ApiModelProperty(value = "物料名称", required = true)
    @NotBlank(message = "物料名称不能为空")
    @Length(min = 1, max = 255, message = "物料料号长度异常")
    private String materialName;

    @ApiModelProperty(value = "物品规格", required = true)
    @NotBlank(message = "物品规格不能为空")
    @Length(min = 1, max = 255, message = "物品规格长度异常")
    private String materialSpecifications;

    @ApiModelProperty(value = "板数", required = true)
    @Length(min = 1, max = 11, message = "板数长度异常")
    private Integer boardCount;

    @ApiModelProperty(value = "箱数", required = true)
    @Length(min = 1, max = 11, message = "箱数长度异常")
    private Integer boxCount;

    @ApiModelProperty(value = "数量", required = true)
    @Length(min = 1, max = 11, message = "数量长度异常")
    private Integer quantityCount;

    @ApiModelProperty(value = "毛重", required = true)
    private BigDecimal grossWeight;

    @ApiModelProperty(value = "净重", required = true)
    private BigDecimal netWeight;

    @ApiModelProperty(value = "体积", required = true)
    private BigDecimal volume;

    @ApiModelProperty(value = "收货需求ID", required = true)
    @NotBlank(message = "收货需求ID")
    private Integer receiptDemandId;

    @ApiModelProperty(value = "批次号", required = true)
    private String batchNumber;

    @ApiModelProperty(value = "单位", required = true)
    @NotBlank(message = "单位不能为空")
    private String unit;

    @ApiModelProperty(value = "单位ID", required = true)
    @NotNull(message = "单位ID不能为空")
    private Integer unitId;
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
    /**
     * 是否收货扫描
     */
    private Integer isReceiptScan;
    /**
     * 是否出货扫描
     */
    private Integer isShipmentScan;
}
