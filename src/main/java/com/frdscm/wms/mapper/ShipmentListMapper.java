package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ShipmentList;
import com.frdscm.wms.entity.vo.ShipmentAppList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 拣货列表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Repository
public interface ShipmentListMapper extends BaseMapper<ShipmentList> {

    /**
     * 根据出货管理ID查询出货明细
     *
     * @param shipmentManageId
     * @return
     */
    @Select("select * from shipment_list where shipment_manage_id = #{shipmentManageId}")
    List<ShipmentList> getShipmentListBytShipmentManageId(@Param("shipmentManageId") Integer shipmentManageId);

    /**
     * 根据出货管理ID查询拣货列表分页
     *
     * @param page
     * @param shipmentManageId
     * @return
     */
    List<ShipmentList> getShipmentListByShipmentManageIdPage(Pagination page, @Param("shipmentManageId") Integer shipmentManageId);


    /**
     * 更具出货管理ID删除拣货列表
     *
     * @param shipmentManageId
     */
    @Delete("delete from shipment_list where shipment_manage_id=#{shipmentManageId}")
    void deleteByShipmentManageId(@Param("shipmentManageId") Integer shipmentManageId);


    /**
     * 已拣货列表
     *
     * @param shipmentDemandId
     * @return
     */
    @Select("SELECT sl.* FROM shipment_list sl INNER JOIN shipment_manage sm ON sl.shipment_manage_id = sm.id WHERE sm.shipment_demand_id = #{shipmentDemandId} and sl.status = 2")
    List<ShipmentAppList> getShipmentListByApp(@Param("shipmentDemandId") Integer shipmentDemandId);

    /**
     * 未拣货列表
     *
     * @param shipmentDemandId
     * @return
     */
    @Select("SELECT sl.* FROM shipment_list sl INNER JOIN shipment_manage sm ON sl.shipment_manage_id = sm.id  WHERE sm.shipment_demand_id = #{shipmentDemandId} and sl.status = 1")
    List<ShipmentList> getShipmentListByNotPick(@Param("shipmentDemandId") Integer shipmentDemandId);


    @Select("SELECT sl.* FROM shipment_list sl INNER JOIN shipment_manage sm ON sl.shipment_manage_id = sm.id WHERE sm.shipment_demand_id = #{shipmentDemandId}")
    List<ShipmentList> getShipmentListByShipmentDemandId(@Param("shipmentDemandId") Integer shipmentDemandId);

    /**
     * 拣货查询（先进先出）
     *
     * @param shipmentDemandId
     * @param warehouseStorageId
     * @return
     */
    @Select("select * from shipment_list where  shipment_manage_id = #{shipmentManageId} and warehouse_storage_id = #{warehouseStorageId} and status = 1 ")
    List<ShipmentList> getShimentListByshipmentTask(@Param("shipmentManageId") Integer shipmentDemandId, @Param("warehouseStorageId") Integer warehouseStorageId);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     */
    @Update("update shipment_list set status = #{status} where id = #{id}")
    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 查询改出货需求下有没有该板号
     *
     * @param shipmentManageId
     * @param boardNumber
     * @return
     */
    @Select("SELECT COUNT(1) FROM `shipment_manage` sm LEFT JOIN shipment_list sl ON sm.id=sl.shipment_manage_id WHERE sm.id=#{shipmentManageId} AND sl.board_number=#{boardNumber}")
    int getReceiptListCount(@Param("shipmentManageId") Integer shipmentManageId, @Param("boardNumber") String boardNumber);


    @Select("select ifnull(SUM(box_count),0) AS boxCount,ifnull(SUM(quantity_count),0) AS quantityCount,ifnull(SUM(gross_weight),0) AS grossWeight,ifnull(SUM(net_weight),0) AS netWeight,ifnull(SUM(volume),0) AS volume FROM shipment_list WHERE shipment_manage_id = #{shipmentManageId} and material_number =#{materialNumber} and status = 2")
    ShipmentList getCountByShipmentManageId(@Param("shipmentManageId") Integer shipmentManageId, @Param("materialNumber") String materialNumber);


    @Select("select * from shipment_list where board_number = #{boardNumber} and shipment_manage_id = #{shipmentManageId}")
    ShipmentList getShipmentListByBoardNumber(@Param("boardNumber") String boardNumber, @Param("shipmentManageId") Integer shipmentManageId);

    @Select("select * from shipment_list where board_number = #{boardNumber}")
    ShipmentList getShipmentListByBoardNumbers(@Param("boardNumber") String boardNumber);

    @Select("select count(id) from shipment_list where shipment_manage_id = #{shipmentManageId} and is_baffle = 1")
    int getBaffle(@Param("shipmentManageId") Integer shipmentManageId);

    @Select("select * from shipment_list where inventory_manage_id = #{inventoryManageId} and shipment_manage_id = #{shipmentManageId}")
    ShipmentList getShipmentList(@Param("shipmentManageId") Integer shipmentManageId, @Param("inventoryManageId") Integer inventoryManageId);
}
