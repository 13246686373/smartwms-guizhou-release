package com.frdscm.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单费用项表
 *
 * @author zhangling
 * @since 2019-04-05
 */
@Data
public class ExpenseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单费用项名称
     */
    private String expenseItemName;
    /**
     * 订单关键数据
     */
    private String orderKeyData;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 费用项价格总计
     */
    private BigDecimal expenseItemTotal;
    /**
     * 是否有效 Y 有效 N 无效
     */
    private String enabledFlag;
    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    private Date createTime;
    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @TableField(update = "now()")
    private Date updateTime;

    @Override
    public String toString() {
        return "ExpenseItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderNo='" + orderNo +
                ", expenseItemName='" + expenseItemName +
                ", orderKeyData='" + orderKeyData +
                ", unitPrice=" + unitPrice +
                ", expenseItemTotal=" + expenseItemTotal +
                ", enabledFlag='" + enabledFlag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
