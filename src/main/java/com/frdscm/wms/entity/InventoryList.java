package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 库存管理物料表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class InventoryList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 可用库存
     */
    private BigDecimal availableStock;
    /**
     * 锁定库存
     */
    private BigDecimal lockStock;
    /**
     * 库存管理ID
     */
    private Integer inventoryManageId;

}
