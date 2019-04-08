package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.WarehouseReconciliation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.dto.WarehouseOrderPageDTO;
import com.frdscm.wms.entity.dto.WarehouseReconciliationPageDTO;
import com.frdscm.wms.entity.vo.WarehouseOrderVO;
import com.frdscm.wms.entity.vo.WarehouseReconciliationVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dizhang
 * @since 2019-04-06
 */
@Repository
public interface WarehouseReconciliationMapper extends BaseMapper<WarehouseReconciliation> {

    @Select("select count(id) from warehouse_reconciliation")
    Integer selectCount();

    void updateStatusById(@Param("idList") List<Integer> idList, @Param("status") Integer status);

    @Delete("delete from warehouse_reconciliation where order_no = '${orderNo}'")
    void deleteByOrderNo(@Param("status") String orderNo);

    List<WarehouseReconciliationVO> selectByPageList(Pagination page, WarehouseReconciliationPageDTO reconciliationPageDTO);
}
