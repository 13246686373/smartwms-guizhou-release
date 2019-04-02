package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/30
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("修改储位等级")
public class UpdateWarehouseStorageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "储位ID")
    @NotNull(message = "储位ID能为空")
    private Integer wareHouseStorageId;

    @ApiModelProperty(value = "储位等级ID")
    @NotNull(message = "储位等级ID不能为空")
    private Integer wareHouseStorageGaradeId;

}
