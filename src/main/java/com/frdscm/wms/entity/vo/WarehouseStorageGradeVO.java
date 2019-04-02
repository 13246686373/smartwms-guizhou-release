package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.frdscm.wms.entity.WarehouseStorage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author March_CD
 * @since 2018-07-27
 */
@Data
public class WarehouseStorageGradeVO implements Serializable {

    private Integer id;
    /**
     * 父级ID
     */
    private Integer pid;
    /**
     * 类型 1-仓 2-层 3-区 4-巷道
     */
    private Integer type;
    /**
     * 编号
     */
    private String number;
    /**
     * 对应区域 1-良品区 2-不良品区 3-待检区 4-待收区 5-发货区
     */
    private Integer region;

    List<WarehouseStorage> warehouseStorageList;

    List<WarehouseStorageGradeVO> warehouseStorageGradeList;

    @Override
    public String toString() {
        return "WarehouseStorageGrade{" +
        ", id=" + id +
        ", pid=" + pid +
        ", type=" + type +
        ", number=" + number +
        "}";
    }
}
