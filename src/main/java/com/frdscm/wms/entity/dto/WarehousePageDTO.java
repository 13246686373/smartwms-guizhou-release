package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: chengdong
 * @description: 查询仓库对象
 */
@Setter
@Getter
@ToString
@ApiModel("仓库")
public class WarehousePageDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 仓库类型ID
     */
    @ApiModelProperty(value = "仓库类型ID", allowEmptyValue = true)
    private Integer typeId;

}
