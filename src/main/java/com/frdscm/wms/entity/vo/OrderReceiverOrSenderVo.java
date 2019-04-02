package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单-收发货联系人表
 * </p>
 *
 * @author huangchunyi
 * @since 2018-06-05
 */
@Data
public class OrderReceiverOrSenderVo {

    private Integer id;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 收发货方ID
     */
    private Integer bid;
    /**
     * 收发货方类型(1-发货方 2-收货方)
     */
    private Integer receiverOrSenderType;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 收发货方代码
     */
    private String code;
    /**
     * 收发货方名称
     */
    private String name;
    /**
     * 收发货时间
     */
    private Date deliveryTime;
    /**
     * 地址
     */
    private String address;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 地址类型(1-发货方 2-收货方)
     */
    private Integer addressType;
    /**
     * 物料清单
     */
    private List<OrderMaterialVo> materialList;
    /**
     * 关联仓库ID
     */
    private Integer associatedWarehouseId;
    /**
     * 关联仓库名称
     */
    private String associatedWarehouseName;
    /**
     * 收货方类型
     */
    private String receiveType;

    @Override
    public String toString() {
        return "OrderReceiverOrSender{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", bid=" + bid +
                ", receiverOrSenderType=" + receiverOrSenderType +
                ", status=" + status +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", deliveryTime=" + deliveryTime +
                '}';
    }

}
