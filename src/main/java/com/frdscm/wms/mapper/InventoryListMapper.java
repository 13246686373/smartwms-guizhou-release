package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.InventoryList;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.GetMaterialVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 库存管理表 Mapper 接口
 *
 * @author dizhang
 * @since 2018-07-07
 */
@Repository
public interface InventoryListMapper extends BaseMapper<InventoryList> {
    @Update("update inventory_list set inventory_manage_id = #{newInventoryManageId} where inventory_manage_id = #{oldInventoryManageId}")
    void mergeUpdateInventoryManage(@Param("oldInventoryManageId") Integer oldInventoryManageId, @Param("newInventoryManageId") Integer newInventoryManageId);

    /**
     * 根据板号(先进先出)
     *
     * @param materialNumber
     * @param clientId
     * @return
     */
    @Select("select * from inventory_list il inner join inventory_manage im on il.inventory_manage_id = il.id where im.lock_status = 0 and il.material_number= #{materialNumber} and il.client_id = #{clientId} and il.warehouse_id = #{warehouseId} order by receipt_time asc")
    List<InventoryList> getFirstIDirstOut(@Param("materialNumber") String materialNumber, @Param("clientId") Integer clientId, @Param("warehouseId") Integer warehouseId);

    @Select("select count(id) from inventory_list where inventory_manage_id = #{inventoryManageId}")
    int getInventoryListCountByInventoryManageId(@Param("inventoryManageId") Integer inventoryManageId);

    @Select("select * from inventory_list where inventory_manage_id = #{inventoryManageId}")
    List<InventoryList> getInventoryListByInventoryManageId(@Param("inventoryManageId") Integer inventoryManageId);

    @Select("select count(id) from inventory_list where inventory_manage_id = #{inventoryManageId}")
    int countList(@Param("inventoryManageId") Integer inventoryManageId);
}
