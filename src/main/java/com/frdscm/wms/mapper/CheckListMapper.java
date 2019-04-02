package com.frdscm.wms.mapper;

import com.frdscm.wms.entity.CheckList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
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
 * @since 2018-08-02
 */
@Repository
public interface CheckListMapper extends BaseMapper<CheckList> {

    /**
     * 更具盘点查询明细
     *
     * @param checkManageId
     * @return
     */
    @Select("select warehouse_storage_number from check_list where check_manage_id = #{checkManageId} and status = 1 group by warehouse_storage_number")
    List<String> getCheckListByCheckManageIdApp(@Param("checkManageId") Integer checkManageId);

    @Select("Select * from check_list where check_manage_id = #{checkManageId}")
    List<CheckList> getCheckListByCheckManageId(@Param("checkManageId") Integer checkManageId);

    /**
     * 更具盘点信息，删除盘点详情
     *
     * @param checkManageId
     */
    @Delete("delete from check_list where check_manage_id = #{checkManageId}")
    void deleteByCheckManageId(@Param("checkManageId") Integer checkManageId);

    /**
     * 修改盘点状态
     *
     * @param status
     * @param checkListId
     */
    @Update("update  check_list set status =#{status} where id = #{checkListId}")
    void updateStatus(@Param("status") Integer status, @Param("checkListId") Integer checkListId);

    /**
     * 移除库位
     *
     * @param
     */
    @Update("update check_list set status = 3 where warehouse_storage_number = #{warehouseStorageNumber} and check_manage_id = #{checkManageId}")
    void updateStatusByWarehouseStorageNumber(@Param("warehouseStorageNumber") String warehouseStorageNumber, @Param("checkManageId") Integer checkManageId);

    /**
     * 查询盘点信息
     *
     * @param boardNumber
     * @return
     */
    @Select("select * from check_list where board_number = #{boardNumber} and warehouse_storage_number = #{warehouseStorageNumber} and  status = 1 and check_manage_id = #{checkManageId}")
    CheckList getCheckList(@Param("boardNumber") String boardNumber, @Param("warehouseStorageNumber") String warehouseStorageNumber, @Param("checkManageId") Integer checkManageId);

    @Select("select count(id) from check_list where check_manage_id = #{checkManageId} and status = 1")
    int getNotCheckCount(@Param("checkManageId") Integer checkManageId);
}
