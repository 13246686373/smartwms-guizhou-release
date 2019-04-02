package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ReceiptManage;
import com.frdscm.wms.entity.dto.CountDTO;
import com.frdscm.wms.entity.dto.ReceiptManagePageDTO;
import com.frdscm.wms.entity.vo.ReceiptCountVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 收货管理表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Repository
public interface ReceiptManageMapper extends BaseMapper<ReceiptManage> {

    /**
     * 根据收货需求ID查询收货管理信息
     *
     * @param receiptDemandId
     * @return
     */
    @Select("select * from receipt_manage where receipt_demand_id = #{receiptDemandId}")
    ReceiptManage selectReceiptManageByReceiptDemandId(@Param("receiptDemandId") Integer receiptDemandId);


    /**
     * 根据收货需求ID删除收货管理信息
     *
     * @param receiptDemandId
     */
    @Delete("delete from receipt_manage where receipt_demand_id = #{receiptDemandId}")
    void deleteReceiptManageByReceiptDemandId(@Param("receiptDemandId") Integer receiptDemandId);

    /**
     * 查询收货列表
     *
     * @param page
     * @param receiptManagePageDTO
     * @return
     */
    List<Map<String, Object>> getReceiptManageByPageList(Pagination page, ReceiptManagePageDTO receiptManagePageDTO);


    /**
     * 修改收货状态
     *
     * @param receiptManageId
     * @param status
     */
    @Update("update receipt_manage set status = #{status} where id = #{receiptManageId}")
    void updateStatus(@Param("receiptManageId") Integer receiptManageId, @Param("status") Integer status);

    /**
     * 看板统计
     *
     * @param receiptCountDTO
     */
    Map getReceiptBoardCount(CountDTO receiptCountDTO);

    /**
     * 看板数据统计
     *
     * @param receiptCountDTO
     */
    List<ReceiptCountVO> getReceiptDataCount(Pagination page, CountDTO receiptCountDTO);

    /**
     * 看板日期统计
     *
     * @param receiptCountDTO
     * @return
     */
    List<Map<String, Object>> getReceiptDateCount(CountDTO receiptCountDTO);

    @Select("select * from receipt_manage where receipt_demand_id = (select id from receipt_demand where single_number = #{singleNumber})")
    ReceiptManage getReceiptManageBySingleNumber(@Param("singleNumber") String singleNumber);
}
