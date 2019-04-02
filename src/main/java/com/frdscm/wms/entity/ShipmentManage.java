package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出货管理表
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Data
public class ShipmentManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 出货需求ID
     */
    private Integer shipmentDemandId;
    /**
     * 状态 1-待出货 2-已拣货 3-已出货
     */
    private Integer status;

    private Integer remove;

    private Date createTime;

    @Override
    public String toString() {
        return "ShipmentManage{" +
                ", id=" + id +
                ", shipmentDemandId=" + shipmentDemandId +
                ", status=" + status +
                "}";
    }
}
