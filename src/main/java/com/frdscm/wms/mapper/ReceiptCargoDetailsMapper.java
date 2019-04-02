package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ReceiptList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收货需求货物明细表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Repository
public interface ReceiptCargoDetailsMapper extends BaseMapper<ReceiptCargoDetails> {

    /**
     * 根据收货需求ID 查询物料信息
     *
     * @param receiptDemandId
     * @return
     */
    @Select("select * from receipt_cargo_details where receipt_demand_id = #{receiptDemandId}")
    List<ReceiptCargoDetails> getReceiptCargoDetailsByReceiptDemandId(@Param("receiptDemandId") Integer receiptDemandId);

    /**
     * 收货汇总
     *
     * @param page
     * @param receiptManageId
     * @return
     */
    @Select("select sd.* FROM receipt_manage rm inner join receipt_cargo_details sd on rm.receipt_demand_id = sd.receipt_demand_id where rm.id = #{receiptManageId} and status != -1")
    List<ReceiptCargoDetails> getReceiptSummary(Pagination page, @Param("receiptManageId") Integer receiptManageId);


//    @Select("select * from receipt_cargo_details where receipt_demand_id = #{receiptDemandId} and material_number = #{materialNumber} and material_name = #{materialName}")
    ReceiptCargoDetails getCargoDetailsByMaterialNumber(@Param("receiptDemandId") Integer receiptDemandId, @Param("receiptList") ReceiptList receiptList);
}
