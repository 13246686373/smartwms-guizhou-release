package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.entity.ShipmentList;
import com.frdscm.wms.entity.ShipmentManage;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.dto.CountDTO;
import com.frdscm.wms.entity.dto.ShipmentManagePageDTO;
import com.frdscm.wms.entity.vo.ShipmentManageListVO;
import com.frdscm.wms.entity.vo.ShipmentsCountVO;

import java.util.List;
import java.util.Map;

/**
 * 出货管理表 服务类
 *
 * @author March_CD
 * @since 2018-07-07
 */
public interface IShipmentManageService extends IService<ShipmentManage> {

    /**
     * 保存出货管理
     *
     * @param shipmentManage
     */
    void save(ShipmentManage shipmentManage);

    /**
     * 根据出货需求ID查询收货
     *
     * @param shipmentDemandId
     * @return
     */
    ShipmentManage selectShipmentManageByShipmentDemandId(Integer shipmentDemandId);


    /**
     * 根据出货需求ID删除收货管理信息
     *
     * @param shipmentDemandId
     */
    void deleteShipmentManageByShipmentDemandId(Integer shipmentDemandId);


    /**
     * * 出货列表分页
     *
     * @param page
     * @param shipmentManagePageDTO
     * @return
     */
    Page<Map<String, Object>> getShipmentManageByPageList(Page<Map<String, Object>> page, ShipmentManagePageDTO shipmentManagePageDTO);


    /**
     * 确认收货需求
     *
     * @param shipmentManageId
     */
    void onConfirm(Integer shipmentManageId);

    /**
     * 出货看板数据
     *
     * @param countDTO
     * @return
     */
    Map getShipmentBoardCount(CountDTO countDTO);

    /**
     * 出货看板数据
     *
     * @param countDTO
     * @return
     */
    Page<ShipmentsCountVO> getShipmentDataCount(CountDTO countDTO);

    /**
     * 出货看板统计日期
     *
     * @param countDTO
     * @return
     */
    List<Map<String, Object>> getShipmentDateCount(CountDTO countDTO);

    void removeTask(Integer shipmentDemandId);

    void completeShipment(Integer id, Integer companyId);
}
