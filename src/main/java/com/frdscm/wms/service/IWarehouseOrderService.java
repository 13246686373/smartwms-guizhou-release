package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.entity.Warehouse;
import com.frdscm.wms.entity.WarehouseOrder;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.WarehouseOrderDTO;
import com.frdscm.wms.entity.dto.WarehouseOrderPageDTO;
import com.frdscm.wms.entity.vo.WarehouseOrderVO;
import com.frdscm.wms.entity.vo.WarehouseVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dizhang
 * @since 2019-04-05
 */
public interface IWarehouseOrderService extends IService<WarehouseOrder> {

    WarehouseOrder save(WarehouseOrderDTO warehouseOrderDTO, UserBO userBO);

    WarehouseOrderDTO getWarehouseOrderById(Integer orderId);

    void delete(Integer id, String orderNo);

    Page<WarehouseOrderVO> selectByPage(Page<WarehouseOrderVO> page, WarehouseOrderPageDTO warehouseOrderPageDTO);

}
