package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.ShipmentCargoDetails;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.GetMaterialVO;
import com.frdscm.wms.entity.vo.InventoryListVO;
import io.swagger.models.auth.In;
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
public interface InventoryManageMapper extends BaseMapper<InventoryManage> {

    /**
     * 查询库存
     *
     * @param inventoryManagePageDTO
     * @return
     */
    List<InventoryManage> getInventoryManageByPageList(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("inventoryManagePageDTO") InventoryManagePageDTO inventoryManagePageDTO);

    int getInventoryManageByPageCount(InventoryManagePageDTO inventoryManagePageDTO);

    /**
     * 修改库存状态
     *
     * @param status 状态 0 - 不良品 1 - 良品
     * @param id     库存ID
     */
    @Update("update inventory_manage set status = #{status} where id = #{id}")
    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);

    @Update("update inventory_manage set lock_status = #{lockStatus} where id = #{id}")
    void updateLockStatus(@Param("id") Integer id, @Param("lockStatus") Integer lockStatus);

    /**
     * 根据板号和储位号查询库存信息
     *
     * @param boardNumber
     * @param warehouseStorageNumber
     * @return
     */
    @Select("select * from inventory_manage where board_number = #{boardNumber} and warehouse_storage_number = #{warehouseStorageNumber}")
    InventoryManage getInventoryManageByBNumAndWsNum(@Param("boardNumber") String boardNumber, @Param("warehouseStorageNumber") String warehouseStorageNumber);


    /**
     * 移库操作
     *
     * @param warehouseStorageNumber
     * @param boardNumber
     */
    @Select("update inventory_manage set warehouse_storage_number = #{warehouseStorageNumber} where board_number=#{boardNumber}")
    void moveWarehouseStorage(@Param("warehouseStorageNumber") String warehouseStorageNumber, @Param("boardNumber") String boardNumber);

    /**
     * 统计板号
     *
     * @param boardNumberLike
     * @return
     */
    int countBoardNumber(@Param("boardNumberLike") String boardNumberLike);

    /**
     * 获取板号是否存在
     *
     * @param boardNumber
     * @return
     */
    int getBoardNumberExist(@Param("boardNumber") String boardNumber);

    /**
     * 查询批次号
     *
     * @param selectbatchNumberDTO
     * @return
     */
    List<String> getBatchNumberList(SelectbatchNumberDTO selectbatchNumberDTO);

    /**
     * 查询库存
     *
     * @return
     */
    List<InventoryManage> getInventoryManageList(CheckManageSelectDTO checkManageSelectDTO);

    /**
     * 出货详情信息
     *
     * @param inventoryManageByShipmentPageDTO
     * @param page
     * @return
     */
    List<InventoryManage> getInventoryManageListByShipment(Pagination page, InventoryManageByShipmentPageDTO inventoryManageByShipmentPageDTO);


    /**
     * 查询物料信息
     *
     * @param materialDTO
     * @return
     */
    List<GetMaterialVO> getMaterialList(MaterialDTO materialDTO);


    /**
     * 根据板号(先进先出)
     *
     * @param materialNumber
     * @param clientId
     * @return
     */
    @Select("select * from inventory_manage where material_number= #{materialNumber} and client_id = #{clientId} and status = 1 and warehouse_id = #{warehouseId} order by receipt_time asc ")
    List<InventoryManage> getInventoryManageByMNumberAndCid(@Param("materialNumber") String materialNumber, @Param("clientId") Integer clientId, @Param("warehouseId") Integer warehouseId);

    /**
     * 更具批次号（更具批次号）
     *
     * @param materialNumber
     * @param clientId
     * @return
     */
    @Select("select * from inventory_manage where material_number= #{materialNumber} and  client_id = #{clientId} and status = 1 and batch_number= #{batchNumber} and warehouse_id = #{warehouseId} order by receipt_time asc ")
    List<InventoryManage> getInventoryManageByMNumberAndCidAndBMumber(@Param("materialNumber") String materialNumber, @Param("clientId") Integer clientId, @Param("batchNumber") String batchNumber, @Param("warehouseId") Integer warehouseId);

    /**
     * 更具板号和物料号查询仓库信息
     *
     * @param boardNumber
     * @param materialNumber
     * @return
     */
    @Select("select * from inventory_manage where board_number = #{boardNumber} and material_number = #{materialNumber} and status = 1  and  client_id = #{clientId}")
    InventoryManage getInventoryManageByBoardNumberAndMaterialNumber(@Param("boardNumber") String boardNumber, @Param("materialNumber") String materialNumber, @Param("clientId") Integer clientId);

    /**
     * 物料查询
     *
     * @param freightInquiryDTO
     * @return
     */
    List<InventoryManage> freightInquiry(FreightInquiryDTO freightInquiryDTO);

    /**
     * 异动更改异动状态
     *
     * @param abnormal
     * @param id
     */
    @Select("update inventory_manage set abnormal= #{abnormal} where id = #{id}")
    void updateAbnormalById(@Param("abnormal") Integer abnormal, @Param("id") Integer id);

    /**
     * 根据板号仓库ID查询库存信息
     *
     * @param warehouseId
     * @param boardNumber
     * @return
     */
    @Select("select * from inventory_manage where board_number = #{boardNumber} and warehouse_id =#{warehouseId} and status !=-1")
    InventoryManage getInventoryManageByWarehouseIdAndBoardNumber(@Param("warehouseId") Integer warehouseId, @Param("boardNumber") String boardNumber);

    @Select("select * from inventory_manage where board_number = #{boardNumber} and warehouse_id =#{warehouseId} and warehouse_storage_number = #{warehouseStorageNumber} and status !=-1")
    InventoryManage getInventoryManageByWarehouseIdAndBoardNumberAndWarehouseStorageNumber(@Param("warehouseId") Integer warehouseId, @Param("warehouseStorageNumber") String warehouseStorageNumber, @Param("boardNumber") String boardNumber);

    @Update("update inventory_manage set status = -1 where id in (select inventory_manage_id from shipment_list where shipment_manage_id = #{shipmentManageId})")
    void removeInventoryByShipmentManageId(@Param("shipmentManageId") Integer shipmentManageId);

    //    @Select("select * from inventory_manage im where im.lock_status = 0 and im.material_number= #{materialNumber} and im.client_id = #{clientId} and im.warehouse_id = #{warehouseId} order by receipt_time asc")
    List<InventoryManage> getFirstIDirstOut(@Param("clientId") Integer clientId, @Param("warehouseId") Integer warehouseId, @Param("shipmentCargoDetails") ShipmentCargoDetails shipmentCargoDetails);

    List<InventoryManage> getInventoryManageByMaterial(@Param("clientId") Integer clientId, @Param("warehouseId") Integer warehouseId, @Param("materialNumbers") List<String> materialNumbers);


    @Select("select DISTINCT single_number from inventory_manage im where im.lock_status = 0 and im.warehouse_id = #{warehouseId} and im.status >= 0")
    List<String> getSingleNumberList(@Param("warehouseId") Integer warehouseId);

    @Select({"<script>",
            "SELECT sum(quantity_count) quantityCount,sum(box_count) boxCount,sum(gross_weight) grossWeightCount FROM inventory_manage",
            "<if test='warehouseId != null'>",
            "WHERE warehouse_id = #{warehouseId}",
            "</if>",
            "</script>"})
    Map<String, Object> getCount(@Param("warehouseId") Integer warehouseId);
}
