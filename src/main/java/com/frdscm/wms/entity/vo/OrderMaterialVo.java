package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 订单-物料表
 * </p>
 *
 * @author huangchunyi
 * @since 2018-06-05
 */
@Data
public class OrderMaterialVo {


    private Integer id;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 收发货联系人ID
     */
    private Integer receiverOrSenderId;
    /**
     * 物料表ID
     */
    private Integer materialsId;
    /**
     * 货物代码(物料料号)
     */
    private String materialItemNumber;
    /**
     * 货物名称
     */
    private String itemName;
    /**
     * 物品规格
     */
    private String itemSpecifications;
    /**
     * 重泡货类型ID(1-无 2-重货 3-泡货)
     */
    private Integer heavyBubbleId;
    /**
     * 重泡货类型名称
     */
    private String heavyBubbleName;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 箱数
     */
    private Integer boxCount;
    /**
     * 板数
     */
    private Integer boardCount;
    /**
     * 毛重(kg)
     */
    private BigDecimal grossWeight;
    /**
     * 净重(kg)
     */
    private BigDecimal netWeight;
    /**
     * 体积(m³)
     */
    private BigDecimal volume;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 单位ID
     */
    private Integer unitId;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 运算规则-数量/箱
     */
    private Integer quantityRule;
    /**
     * 运算规则-箱数/板
     */
    private Integer boxCountRule;
    /**
     * 运算规则-单位毛重
     */
    private BigDecimal grossWeightRule;
    /**
     * 运算规则-单位净重
     */
    private BigDecimal netWeightRule;
    /**
     * 运算规则-单位体积
     */
    private BigDecimal volumeRule;


    @Override
    public String toString() {
        return "OrderMaterial{" +
        ", id=" + id +
        ", orderId=" + orderId +
        ", materialsId=" + materialsId +
        ", materialItemNumber=" + materialItemNumber +
        ", itemName=" + itemName +
        ", itemSpecifications=" + itemSpecifications +
        ", heavyBubbleId=" + heavyBubbleId +
        ", heavyBubbleName=" + heavyBubbleName +
        ", quantity=" + quantity +
        ", boxCount=" + boxCount +
        ", boardCount=" + boardCount +
        ", grossWeight=" + grossWeight +
        ", netWeight=" + netWeight +
        ", volume=" + volume +
        "}";
    }
}
