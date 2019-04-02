package com.frdscm.wms.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/11
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("查询扫描列表")
public class ReceiptScanListPageDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("收货管理ID")
    private Integer receiptManageId;
}
