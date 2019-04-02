package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
@Data
public class CheckManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 盘点类型ID
     */
    private Integer checkTypeId;
    /**
     * 盘点类型名称
     */
    private String checkTypeName;
    /**
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 盘点单号
     */
    private String checkNumber;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态 1-未完成 2-已完成
     */
    private Integer status;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 料号
     */
    private String materialNumber;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 收货时间开始
     */
    private Date receiptTimeStart;
    /**
     * 收货时间结束
     */
    private Date receiptTimeEnd;
    /**
     * 附件路径
     */
    private String filePath;

    private String fileName;

    @Override
    public String toString() {
        return "CheckManage{" +
                ", id=" + id +
                ", checkTypeId=" + checkTypeId +
                ", checkTypeName=" + checkTypeName +
                ", warehouseId=" + warehouseId +
                ", checkNumber=" + checkNumber +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                "}";
    }
}
