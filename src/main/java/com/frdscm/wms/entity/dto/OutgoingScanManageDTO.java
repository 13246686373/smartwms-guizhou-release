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
@ApiModel("扫描管理")
public class OutgoingScanManageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "出货扫描管理ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "出货管理ID")
    private Integer shipmentManageId;

    @ApiModelProperty(value = "是否扫描箱号    1-扫描 2-不扫描")
    private Integer isBoxNum;

    @ApiModelProperty(value = "是否扫描子箱号  1-扫描 2-不扫描")
    private Integer isBoxSonNum;

    @ApiModelProperty(value = "是否扫描序列号  1-扫描 2-不扫描")
    private Integer isSerialNum;

    @ApiModelProperty(value = "是否扫描序板号  1-扫描 2-不扫描")
    private Integer isBoardNum;

    @ApiModelProperty(value = "是否开启出库扫描  1-扫描 2-不扫描")
    private Integer isScan;
}
