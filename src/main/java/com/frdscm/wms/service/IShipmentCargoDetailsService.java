package com.frdscm.wms.service;

import com.frdscm.wms.entity.ShipmentCargoDetails;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.dto.ShipmentCargoDetailsDTO;
import com.frdscm.wms.entity.vo.ShipmentAppList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 出货需求货物明细表 服务类
 *
 * @author March_CD
 * @since 2018-07-07
 */
public interface IShipmentCargoDetailsService extends IService<ShipmentCargoDetails> {

    /**
     * 保存收货明细
     * @param shipmentCargoDetailsDTO
     */
    void save(ShipmentCargoDetailsDTO shipmentCargoDetailsDTO);


    /**
     * 查询出货明细
     * @param shipmentDemandId
     * @return
     */

    List<ShipmentCargoDetails>  getShipmentCargoDetailsList(Integer shipmentDemandId);



    /**
     * 查询拣货任务
     * @param shipmentDemandId
     * @return
     */
    ShipmentAppList shipmentTaskDetailRandom(Integer shipmentDemandId);

    /**
     *
     * @param id
     * @return
     */
    ShipmentCargoDetails getShipmentCargoDetails(Integer id);
}
