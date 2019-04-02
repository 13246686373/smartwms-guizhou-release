package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ShipmentDemand;
import com.frdscm.wms.entity.ShipmentManage;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.ShipmentDemandDTO;
import com.frdscm.wms.entity.dto.ShipmentDemandPageDTO;
import com.frdscm.wms.entity.vo.ShipmentDemandVO;

import java.util.List;
import java.util.Map;

/**
 * 出货需求表 服务类
 *
 * @author March_CD
 * @since 2018-07-07
 */
public interface IShipmentDemandService extends IService<ShipmentDemand> {

    /**
     * 根据ID查询出货需求表
     *
     * @param id
     * @return
     */
    ShipmentDemand selectShipmentDemandById(Integer id);

    /**
     * 保存出货需求
     *
     * @param shipmentDemandDTO
     * @param userBO            用户
     */
    ShipmentDemandDTO save(ShipmentDemandDTO shipmentDemandDTO, UserBO userBO);

    /**
     * 编辑出货需求
     *
     * @param shipmentDemandDTO
     */
    ShipmentDemandDTO edit(ShipmentDemandDTO shipmentDemandDTO);

    /**
     * 查询出货需求列表
     *
     * @param page
     * @param shipmentDemandPageDTO
     * @return
     */
    Page<ShipmentDemandVO> getShipmentDemandByPageList(Page<ShipmentDemandVO> page, ShipmentDemandPageDTO shipmentDemandPageDTO);

    /**
     * 确认出货需求
     * @param status
     * @param shipmentDemandId
     */
    void updateStatus(Integer status,Integer shipmentDemandId);

    /**
     * 确认出货需求
     */
    void confirmDemand(ShipmentDemand shipmentDemand, UserBO userBO);

    /**
     * 取消出货需求
     */
    void cancelDemand(ShipmentDemand shipmentDemand, ShipmentManage shipmentManage);

    /**
     * 删除出货需求
     * @param id
     */
    void deleteByShipmentDemandId(Integer id);

    /**
     * 获取拣货任务
     * @param warehouseId
     * @return
     */
    List<ShipmentDemand> getShipmentDemandByTaskList(Integer warehouseId);


}
