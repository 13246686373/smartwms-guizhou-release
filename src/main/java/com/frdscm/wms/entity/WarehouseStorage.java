package com.frdscm.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 仓库储位表
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Data
public class WarehouseStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 储位编号
     */
    private String number;
    /**
     * 储位类型ID
     */
    private Integer typeId;
    /**
     * 储位类型名称
     */
    private String typeName;
    /**
     * 储位容量
     */
    private BigDecimal capacity;
    /**
     * 储位长
     */
    private BigDecimal length;
    /**
     * 储位宽
     */
    private BigDecimal width;
    /**
     * 储位高
     */
    private BigDecimal height;
    /**
     * 备注
     */
    private String remark;
    /**
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(update = "now()")
    private Date updateTime;
    /**
     * 状态 1-正常 -1-删除
     */
    @JSONField(serialize = false)
    private Integer status;

    @Override
    public String toString() {
        return "WarehouseStorage{" +
                ", id=" + id +
                ", number=" + number +
                ", typeId=" + typeId +
                ", typeName=" + typeName +
                ", capacity=" + capacity +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", remark=" + remark +
                ", warehouseId=" + warehouseId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                "}";
    }
}
