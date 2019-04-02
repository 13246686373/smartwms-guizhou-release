package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("拆板Iem DTO")
public class DismantlingPlateItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空")
    private Integer id;

    @NotNull(message = "拆板量不能为空")
    private Integer quantityCount;
}
