package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/3
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("统计")
public class CountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始日期", required = true)
    private Long receiptTimeStart;

    @ApiModelProperty(value = "结束日期", required = true)
    private Long receiptTimeEnd;

    @ApiModelProperty(value = "客户ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "仓库ID", required = true)
    private Integer warehouseId;


    private Integer pageNo;

    private Integer pageSize;
}
