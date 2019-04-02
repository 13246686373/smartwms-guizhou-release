package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: dizhang
 * @Date: 2018/8/16
 * @Desc:
 **/
@Data
public class ReceiptListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 板号
     */
    private String boardNumber;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 物料料号
     */
    private String materialNumber;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 储位号
     */
    private String warehouseStorageNumber;
    /**
     * 收货管理ID
     */
    private Integer receiptManageId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单位ID
     */
    private Integer unitId;

    /**
     * 状态 1-收货上架 2-已收货上架
     */
    private Integer status;

    /**
     * 数量
     */
    private BigDecimal count;
}
