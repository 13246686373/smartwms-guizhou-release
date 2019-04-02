package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/10
 * @Desc:
 **/
@Data
public class CheckListVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 板号
     */
    private String boardNumber;
    /**
     * 储位号
     */
    private String warehouseStorageNumber;
    /**
     * 料号
     */
    private String materialNumber;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 单位
     */
    private String unit;

    /**
     * 需要盘点数量
     */
    private Object checkCount;

    /**
     * 盘点管理ID
     */
    private Integer checkManageId;

    /**
     * 单位ID
     */
    private Integer unitId;
}
