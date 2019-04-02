package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.vo.MaterialListAppVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 收货列表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Repository
public interface ReceiptListMapper extends BaseMapper<ReceiptList> {

    /**
     * 根据收货管理ID查询物料明细
     *
     * @param receiptManageId 收货管理ID
     * @return List<ReceiptList> 物料明细List
     */
    @Select("select * from receipt_list where receipt_manage_id = #{receiptManageId} and status != -1")
    List<ReceiptList> getReceiptListByReceiptManageId(@Param("receiptManageId") Integer receiptManageId);

    @Select("select sd.* FROM receipt_manage rm inner join receipt_cargo_details sd on rm.receipt_demand_id = sd.receipt_demand_id where rm.id = #{receiptManageId} and status != -1")
    List<ReceiptCargoDetails> getReceiptCargoDetailsByReceiptManageId(@Param("receiptManageId") Integer receiptManageId);

    /**
     * 根据收货管理ID查询物料明细分页
     *
     * @param receiptManageId
     * @return
     */
    List<ReceiptList> getReceiptListByReceiptManageIdPage(Pagination page, @Param("receiptManageId") Integer receiptManageId);

    /**
     * 根据收货管理ID 查询物料信息
     *
     * @param receiptManageId
     * @return
     */
    @Select("SELECT DISTINCT d.material_number,d.material_name FROM receipt_manage m inner join receipt_cargo_details d on m.receipt_demand_id = d.receipt_demand_id WHERE m.id = #{receiptManageId}")
    List<MaterialListAppVO> getMaterialListByReceiptManageId(@Param("receiptManageId") Integer receiptManageId);

    /**
     * 删除收货详情
     *
     * @param receiptManageId
     */
    @Update("update receipt_list set status = -1  where  receipt_manage_id = #{receiptManageId} ")
    void deleteByReceiptManageId(@Param("receiptManageId") Integer receiptManageId);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     */
    @Update("update receipt_list set status=#{status} where id = #{id}")
    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 查询未收货上架的货物
     *
     * @param receiptManageId
     * @return
     */
    @Select("select * from receipt_list where receipt_manage_id = #{receiptManageId} and status = 1")
    List<ReceiptList> getReceiptListByReceiptManageIdStatus(@Param("receiptManageId") Integer receiptManageId);

    @Select("select count(id) from receipt_list where receipt_manage_id = #{receiptManageId} and status = 1")
    int getReceiptListNotShelf(@Param("receiptManageId") Integer receiptManageId);
}
