package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/8/2
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("盘点筛选")
public class CheckManageSelectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库ID", required = true)
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "收货开始日期", required = true)
    private String receiptTimeStart;

    @ApiModelProperty(value = "收货结束日期", required = true)
    private String receiptTimeEnd;

    @ApiModelProperty(value = "客户ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "物料料号", required = true)
    private String materialNumber;

    @ApiModelProperty(value = "选择异动盘点的时候传值1", required = true)
    private Boolean abnormal;

}
