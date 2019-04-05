package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.ShipmentCargoDetails;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.GetMaterialVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 移位、调拨表 Mapper 接口
 *
 * @author dizhang
 * @since 2018-07-07
 */
@Repository
public interface InventoryManageRecordMapper extends BaseMapper<InventoryManage> {

    /**
     * 移位、调拨列表
     *
     * @param inventoryManagePageDTO
     * @return
     */
    List<InventoryManage> getInventoryManageRecordByPageList(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("inventoryManagePageDTO") InventoryManagePageDTO inventoryManagePageDTO,@Param("tableName") String tableName);

    int getInventoryManageRecordByPageCount(@Param("inventoryManagePageDTO") InventoryManagePageDTO inventoryManagePageDTO,@Param("tableName") String tableName);
}
