package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/6
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("收货管理对象")
public class ReceiptManageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库管理对象ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "收货需求ID", required = true)
    private Integer receiptDemandId;
    /**
     * 状态 1-待收货 2-已收货
     */
    @ApiModelProperty(value = " 收货管理状态ID  1 待收货 2 已收货", required = true)
    @NotNull(message = "收货管理状态ID不能为空")
    private Integer status;

    @ApiModelProperty(value = "是否计量收货 1 是 0 否", required = true)
    private Integer isMeasureReceipt;

    @ApiModelProperty(value = "是否计重收货 1 是 0 否", required = true)
    private Integer isWeightReceipt;

    @ApiModelProperty(value = "是否计体积收货 1 是 0 否", required = true)
    private Integer isVolumeReceipt;




}
