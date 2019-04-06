package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/7/6
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("收货报表、出货报表分页")
public class ReceiptReportFormDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户简称ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "仓库ID", required = true)
    private List<Integer> warehouseFilters;

    @ApiModelProperty(value = "入仓时间开始", required = true)
    private String warehousingTimeStart;

    @ApiModelProperty(value = "入仓时间结束", required = true)
    private String warehousingTimeEnd;

    @ApiModelProperty(value = "收货方式、出货方式ID", required = true)
    private Integer modeId;

    @ApiModelProperty(value = "单号", required = true)
    private String singleNumber;

    @ApiModelProperty(value = "物料号", required = true)
    private String materialNumber;
}
