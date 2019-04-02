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
 * 仓库表
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Data
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 仓库代码
     */
    private String code;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * 仓库类型ID
     */
    private Integer typeId;
    /**
     * 仓库类型名称
     */
    private String typeName;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 所属片区
     */
    private String areaName;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 维度
     */
    private BigDecimal latitude;
    /**
     * 备注
     */
    private String remark;
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
    /**
     * 操作人ID
     */
    @JSONField(serialize = false)
    private Integer operatorId;
    /**
     * 操作人
     */
    @JSONField(serialize = false)
    private String operatorName;
    /**
     * 状态 1-正常 -1-删除
     */
    @JSONField(serialize = false)
    private Integer status;

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", principal='" + principal + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", areaName='" + areaName + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                ", status=" + status +
                '}';
    }
}
