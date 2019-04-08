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
 * 订单货物明细表
 *
 * @author zhangling
 * @since 2019-04-05
 */
@Data
public class CargoDetails implements Serializable {

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
     * 物料
     */
    private String materialsName;
    /**
     * 产品规格
     */
    private String specificationGrade;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 单位ID
     */
    private Integer unitId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 数量
     */
    private BigDecimal quantity;
    /**
     * 体积
     */
    private BigDecimal volume;
    /**
     * 是否有效: Y 有效 N失效
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
        return "CargoDetails{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderNo='" + orderNo +
                ", materialsName='" + materialsName +
                ", specificationGrade='" + specificationGrade +
                ", batchNo='" + batchNo +
                ", unit='" + unit +
                ", quantity=" + quantity +
                ", volume=" + volume +
                ", enabledFlag=" + enabledFlag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';

    }
}
