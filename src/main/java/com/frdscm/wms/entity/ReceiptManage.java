package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 收货管理表
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Data
public class ReceiptManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 收货需求ID
     */
    private Integer receiptDemandId;
    /**
     * 状态 1-待收货 2-待入储 3-已收货
     */
    private Integer status;

    @Override
    public String toString() {
        return "ReceiptManage{" +
                ", id=" + id +
                ", receiptDemandId=" + receiptDemandId +
                ", status=" + status +
                "}";
    }
}
