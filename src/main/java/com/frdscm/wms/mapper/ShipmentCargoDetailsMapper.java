package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ShipmentCargoDetails;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.ShipmentList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出货需求货物明细表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Repository
public interface ShipmentCargoDetailsMapper extends BaseMapper<ShipmentCargoDetails> {


    /**
     * 根据出货需求ID查询出货明细
     *
     * @param shipmentDemandId
     * @return
     */
    @Select("select * from shipment_cargo_details where shipment_demand_id = #{shipmentDemandId}")
    List<ShipmentCargoDetails> getShipmentCargoDetailsListByShipmentDemandId(@Param("shipmentDemandId") Integer shipmentDemandId);

    /**
     * 根据出货需求ID查询出货明细
     *
     * @param shipmentDemandId
     * @return
     */
    @Select("select * from shipment_cargo_details where shipment_demand_id = #{shipmentDemandId}")
    List<ShipmentCargoDetails> shipmentScanningRandom(@Param("shipmentDemandId") Integer shipmentDemandId);

    /**
     * 根据出货需求删除出货信息
     *
     * @param shipmentDemandId
     */
    @Delete("delete from shipment_cargo_details where shipment_demand_id = #{shipmentDemandId} ")
    void deleteByShipmentDemandId(@Param("shipmentDemandId") Integer shipmentDemandId);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     */
    @Update("update shipment_cargo_details set status = #{status} where id = #{id} ")
    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 收货汇总
     *
     * @param page
     * @param shipmentManageId
     * @return
     */
    @Select("select sd.* FROM shipment_manage rm inner join shipment_cargo_details sd on rm.shipment_demand_id = sd.shipment_demand_id where rm.id = #{shipmentManageId} and status != -1")
    List<ShipmentCargoDetails> getShipmentSummary(Pagination page, @Param("shipmentManageId") Integer shipmentManageId);

    ShipmentCargoDetails getCargoDetailsByMaterialNumber(@Param("shipmentDemandId") Integer shipmentDemandId, @Param("shipmentList") InventoryManage inventoryManage);

    ShipmentCargoDetails getCargoDetailsByMaterialNumbers(@Param("shipmentDemandId") Integer shipmentDemandId, @Param("shipmentList") ShipmentList shipmentList);

    @Select("select DISTINCT material_number from shipment_cargo_details where shipment_demand_id = #{shipmentDemandId}")
    List<String> getMaterialNumbers(@Param("shipmentDemandId") Integer shipmentDemandId);
}
