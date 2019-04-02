package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/2
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("明细更新")
public class CheckListUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "盘点明细ID（编辑时必传）")
    private  Integer id;


    @ApiModelProperty(value = "盘点结果（编辑时必传）")
    private  Integer checkResult;

    @ApiModelProperty(value = "盘点数量（编辑时必传）")
    private Integer checkSum;
}
