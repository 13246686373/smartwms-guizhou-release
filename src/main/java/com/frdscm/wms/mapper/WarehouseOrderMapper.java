package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.WarehouseOrder;
import com.frdscm.wms.entity.dto.WarehouseOrderPageDTO;
import com.frdscm.wms.entity.vo.WarehouseOrderVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dizhang
 * @since 2019-04-05
 */
@Repository
public interface WarehouseOrderMapper extends BaseMapper<WarehouseOrder> {


    @Select("select * from warehouse_order where enabled_flag = 'Y'")
    List<WarehouseOrder> getWarehouseOrderList();

    @Select("select count(id) from warehouse_order")
    Integer selectCount();

    @Select("update warehouse_order set enabled_flag = 'N' where id = ${id}")
    void updateEnabledFlag(@Param("id") Integer id);

    /**
     * 根据订单编号获取订单信息
     * @param orderNo
     * @return
     */
    @Select("select * from warehouse_order where enabled_flag = 'Y' and order_no = '${orderNo}'")
    WarehouseOrder getWarehouseOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 获取仓库订单列表
     * @return
     */
    List<WarehouseOrderVO> selectByPageList(Pagination page, WarehouseOrderPageDTO warehousePageOrderDTO);

}
