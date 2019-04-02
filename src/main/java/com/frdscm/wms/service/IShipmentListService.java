package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ShipmentList;
import com.frdscm.wms.entity.dto.ScanningDTO;
import com.frdscm.wms.entity.dto.ShipmentListDTO;
import com.frdscm.wms.entity.vo.ShipmentAppList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 拣货列表 服务类
 *
 * @author March_CD
 * @since 2018-07-07
 */
public interface IShipmentListService extends IService<ShipmentList> {

    /**
     * 保存拣货列表
     *
     * @param shipmentListDTO
     */
    void save(ShipmentListDTO shipmentListDTO);

    /**
     * 根据出货管理ID 查询出货明细
     *
     * @param shipmentManageId
     * @return
     */
    List<ShipmentList> getShipmentListBytShipmentManageId(Integer shipmentManageId);

    /**
     * 根据出货管理ID 查询出货明细
     *
     * @param shipmentManageId
     * @param shipmentManageId
     * @return
     */
    Page<ShipmentList> getShipmentListByPageShipmentManageId(Page<ShipmentList> page, Integer shipmentManageId);


    /**
     * 删除拣货明细
     *
     * @param shipmentManageId
     */
    void deleteByShipmentManageId(Integer shipmentManageId);

    /**
     * 获取已扫描的列表
     * @return
     */
    List<ShipmentAppList> getShipmentListByApp(Integer shipmentDemandId);


    /**
     * 定点拣货，先进先出
     *
     * @return
     */
    ShipmentAppList getShimentListByshipmentTask(Integer shipmentDemandId);

    /**
     * 定点拣货，先进先出
     *
     * @return
     */
    ShipmentList getShipmentTaskDetail(Integer shipmentId);


    ShipmentList getShipmentTaskDetailRandom(Integer shipmentId);

    /**
     * 定点拣货扫描，先进先出
     *
     * @param scanningDTO
     * @return
     */
    Map<String, BigDecimal> getScanning(ScanningDTO scanningDTO);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Integer id);

    /**
     * 随机拣货扫描
     */
    Map<String, BigDecimal> getScanningRandom(ScanningDTO scanningDTO);


    void quicklyPick(Integer shipmentManageId);

    ShipmentList addPick(ShipmentListDTO shipmentListDTO);

    void delete(Integer id);
}
