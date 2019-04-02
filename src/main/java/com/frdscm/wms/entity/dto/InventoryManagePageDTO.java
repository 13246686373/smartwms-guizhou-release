package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: dizhang
 * @Date: 2018/7/10
 * @Desc: 库存对象
 **/
@Setter
@Getter
@ToString
@ApiModel("库存对象")
public class InventoryManagePageDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库ID", required = true)
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "客户简称ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "客户简称名称", required = true)
    private String clientName;

    @ApiModelProperty(value = "收货时间开始", required = true)
    private String receiptTimeStart;

    @ApiModelProperty(value = "收货时间结束", required = true)
    private String receiptTimeEnd;

    @ApiModelProperty(value = "收货单号", required = true)
    private String singleNumber;
}
