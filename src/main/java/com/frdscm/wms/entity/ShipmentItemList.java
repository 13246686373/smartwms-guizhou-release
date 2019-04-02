package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出货列表
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Data
public class ShipmentItemList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 出货列表ID
     */
    private Integer shipmentListId;
}
