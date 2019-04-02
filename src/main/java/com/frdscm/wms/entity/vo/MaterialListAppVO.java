package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/18
 * @Desc:
 **/
@Data
public class MaterialListAppVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物料料号
     */
    private String materialNumber;
    /**
     * 物料名称
     */
    private String materialName;

}
