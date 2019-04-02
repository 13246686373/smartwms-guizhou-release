package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("收货需求明细对象")
public class ReceiptListPageDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收货管理ID", required = true)
    private Integer receiptManageId;

}
