package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ReceiptDemand;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.dto.InventoryManagePageDTO;
import com.frdscm.wms.entity.dto.ReceiptDemandPageDTO;
import com.frdscm.wms.entity.vo.ReceiptDemandAppVO;
import com.frdscm.wms.entity.vo.ReceiptDemandVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 收货需求表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Repository
public interface ReceiptDemandMapper extends BaseMapper<ReceiptDemand> {

    /**
     * 查询收货需求表
     * @param page
     * @param receiptDemandPageDTO
     * @return
     */
    List<ReceiptDemandVO> getReceiptCargoDetailsList(Pagination page,  ReceiptDemandPageDTO receiptDemandPageDTO);

    /**
     * 查询收货需求APP
     * @return
     */
    List<ReceiptDemandAppVO> getReceipetManageByApp(@Param("warehouseId") Integer warehouseId);

    /**
     * 查询待上架的列表
     *
     * @return
     */
    List<ReceiptDemandAppVO> getReceipetManageByShelf(@Param("warehouseId") Integer warehouseId);
    /**
     * 修改收货状态
     * @param status
     */
    @Update("update receipt_demand set status = #{status} where id=#{id}")
    void updateStatus(@Param("status")Integer status, @Param("id")Integer id);

    /**
     * 更具板号查询收货需求
     * @param boardNumber
     * @return
     */
    @Select("select * from receipt_list where  board_number= #{boardNumber}")
    ReceiptList getReceiptListByBoardNumber(@Param("boardNumber") String  boardNumber);

    /**
     * 根据收货管理ID 查询收货需求
     * @param receiptManageId
     * @return
     */
    @Select("SELECT * FROM receipt_demand WHERE id = (SELECT receipt_demand_id FROM receipt_manage WHERE id = #{receiptManageId})")
    ReceiptDemand getReceiptDemandByReceiptManageId(@Param("receiptManageId") Integer receiptManageId);


    @Select("select ifnull(max(right(single_number, 3)),0) + 1 from receipt_demand where single_number like concat(#{singleNumber},'%')")
    int countSingleNumber(@Param("singleNumber") String singleNumber);

    /**
     * 根据订单号删除收货需求
     * @param orderNo
     */
    @Delete("delete from receipt_demand where order_number = '${orderNo}'")
    void deleteByOrderNo(@Param("orderNo") String orderNo);
}
