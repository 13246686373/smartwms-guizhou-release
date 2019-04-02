package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.InventoryScanList;
import com.frdscm.wms.entity.ReceiptScanList;
import com.frdscm.wms.entity.bo.ShipmentScanListBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Repository
public interface InventoryScanListMapper extends BaseMapper<InventoryScanList> {
    @Update("update inventory_scan_list set inventory_manage_id = #{newInventoryManageId} where inventory_manage_id = #{oldInventoryManageId}")
    void mergeUpdateInventoryScanList(@Param("oldInventoryManageId") Integer oldInventoryManageId, @Param("newInventoryManageId") Integer newInventoryManageId);

    @Select("select * from inventory_scan_list where inventory_manage_id = #{inventoryManageId}")
    List<ShipmentScanListBO> exportExcel(@Param("inventoryManageId") Integer inventoryManageId);
}
