package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <p>
 * 订单-车型表
 * </p>
 *
 * @author huangchunyi
 * @since 2018-06-05
 */
@Data
public class OrderCarTypeVo{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 车型ID
     */
    private Integer carTypeId;
    /**
     * 车型名称
     */
    private String carTypeName;

    @Override
    public String toString() {
        return "OrderCarType{" +
        ", id=" + id +
        ", orderId=" + orderId +
        ", carTypeId=" + carTypeId +
        ", carTypeName=" + carTypeName +
        "}";
    }
}
