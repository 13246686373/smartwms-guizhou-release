package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: chengdong
 * @description: 查询仓库对象
 */
@Data
@ToString
@ApiModel("仓库")
public class WarehouseReconciliationPageDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应收对账单号")
    private String reconciliationNo;

    @ApiModelProperty(value = "客户名称")
    private String clientName;

    @ApiModelProperty(value = "对账单状态 0 未对账  1 对账单确认 2 财务确认")
    private Integer status;

    @ApiModelProperty(value = "合同编号")
    private String contractNo;



}
