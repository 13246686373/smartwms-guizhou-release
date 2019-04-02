package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.WarehouseStorage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 仓库储位表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Repository
public interface WarehouseStorageMapper extends BaseMapper<WarehouseStorage> {
    /**
     * 统计以仓库名称的储位
     *
     * @param number
     * @param warehouseId
     * @return
     */
    @Select("SELECT COUNT(id) FROM warehouse_storage where number = #{number} and warehouse_id = #{warehouseId}")
    int selectCount(@Param("number") String number, @Param("warehouseId") Integer warehouseId);

    /**
     * 查询仓库名称的储位
     *
     * @param number
     * @param warehouseId
     * @return
     */
    @Select("SELECT * FROM warehouse_storage where number = #{number} and warehouse_id = #{warehouseId}")
    WarehouseStorage selectOne(@Param("number") String number, @Param("warehouseId") Integer warehouseId);

    /**
     * 根据储位等级ID 统计储位个数
     *
     * @param wsgId
     * @return
     */
    @Select("select count(1) from warehouse_storage where warehouse_storage_grade_id = #{wsgId} and status=1")
    int countWareHouseStorageByWsgId(@Param("wsgId") Integer wsgId);

    /**
     * 修改储位等级
     *
     * @param wsgId
     * @param wsId
     */
    @Update("update warehouse_storage set  warehouse_storage_grade_id=#{wsgId} where id = #{wsId}")
    void updateWareHouseStorage(@Param("wsgId") Integer wsgId, @Param("wsId") Integer wsId);

    /**
     * 更具储位等级ID查询下面的储位
     *
     * @param wsgId
     * @return
     */
    @Select("select * from warehouse_storage where warehouse_storage_grade_id = #{wsgId} ")
    List<WarehouseStorage> getWarehouseStorageListByWsgId(@Param("wsgId") Integer wsgId);

    // @Select("select number from warehouse_storage where warehouse_storage_grade_id = #{wsgId}")
//    List<String> getWareHouseStorageNumberByWsgId(@Param("wsgId") Integer wsgId);

    /**
     * 更具储位号查询储位ID
     *
     * @param number
     * @return
     */
    @Select("select id from warehouse_storage where number = #{number}")
    Integer getIdByNumber(@Param("number") String number);

    /**
     * 更具id查询储位号
     *
     * @param id
     * @return
     */
    @Select("select number from warehouse_storage where id = #{id}")
    String getNumberById(@Param("id") Integer id);


    @Select("select * from warehouse_storage where number = #{number} and warehouse_id = #{warehouseId}")
    WarehouseStorage getWarehouseStorageByNumberAndWarehouseId(@Param("number") String number, @Param("warehouseId") Integer warehouseId);


    @Select("select ifnull(sum(capacity),0) capacitySum,count(id) warehouseStorageCount from warehouse_storage where warehouse_id = #{warehouseId} and status = 1")
    Map<String, Object> getScaleSummary(@Param("warehouseId") Integer warehouseId);

    @Select("select sum(count) from(select count(rl.id) count from receipt_list rl inner join receipt_manage rm on rl.receipt_manage_id = rm.id inner join receipt_demand rd on rm.receipt_demand_id = rd.id where rd.warehouse_id = #{warehouseId} and rl.warehouse_storage_number = #{warehouseStorageNumber} and rl.status = 2 and rm.status != 3 union all select count(id) count from inventory_manage where warehouse_id = #{warehouseId} and warehouse_storage_number = #{warehouseStorageNumber} and status != -1) a")
    int getStorageNumberCount(@Param("warehouseId") Integer warehouseId, @Param("warehouseStorageNumber") String warehouseStorageNumber);
}
