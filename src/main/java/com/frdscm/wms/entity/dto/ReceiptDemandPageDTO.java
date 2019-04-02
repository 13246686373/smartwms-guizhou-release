package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/5
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("收货需求分页")
public class ReceiptDemandPageDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户简称ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "客户简称名称", required = true)
    private String clientName;

    @ApiModelProperty(value = "仓库ID",required = true)
    private Integer warehouseId;

    @ApiModelProperty(value = "收货类型ID",required = true)
    private Integer typeId;

    @ApiModelProperty(value = "收货类型名称",required = true)
    private String typeName;

    @ApiModelProperty(value = "需求来源ID名称",required = true)
    private Integer sourceId;

    @ApiModelProperty(value = "需求来源名称", required = true)
    private String sourceName;

    @ApiModelProperty(value = "操作模式ID", required = true)
    private Integer operatingModeId;

    @ApiModelProperty(value = "操作模式名称", required = true)
    private String operatingModeName;
}
