package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ReceiptDemand;
import com.frdscm.wms.entity.ShipmentDemand;
import com.frdscm.wms.entity.dto.CountDTO;
import com.frdscm.wms.entity.dto.ShipmentDemandPageDTO;
import com.frdscm.wms.entity.vo.ShipmentDemandVO;
import com.frdscm.wms.entity.vo.ShipmentsCountVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 出货需求表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Repository
public interface ShipmentDemandMapper extends BaseMapper<ShipmentDemand> {

    /**
     * 查询出货列表
     *
     * @param page
     * @param shipmentDemandPageDTO
     * @return
     */
    List<ShipmentDemandVO> getShipmentDemandByPageList(Pagination page, ShipmentDemandPageDTO shipmentDemandPageDTO);

    /**
     * 修改出货状态
     *
     * @param status
     */
    @Update("update shipment_demand set status = #{status} where id=#{id}")
    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);


    /**
     * 根据仓库ID查询收货需求ID
     *
     * @param warehouseId
     * @return
     */
    @Select("select sd.* from shipment_manage sm inner join shipment_demand sd on sm.shipment_demand_id = sd.id where sd.warehouse_id = #{warehouseId} and sm.status = 1 order by sm.create_time asc")
    List<ShipmentDemand> getShipmentDemandByTaskList(@Param("warehouseId") Integer warehouseId);

    @Select("select ifnull(max(right(single_number, 3)),0) + 1 from shipment_demand where single_number like concat(#{singleNumber},'%')")
    int countSingleNumber(@Param("singleNumber") String singleNumber);


    @Select("SELECT * FROM shipment_demand WHERE id = (SELECT shipment_demand_id FROM shipment_manage  WHERE id = #{shipmentManageId})")
    ShipmentDemand getShipmentDemandByShipmentManageId(@Param("shipmentManageId") Integer shipmentManageId);
}
