package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ShipmentScanList;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.ShipmentScanListDTO;
import com.frdscm.wms.entity.vo.OutgoingScanVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
public interface IShipmentScanListService extends IService<ShipmentScanList> {

    /**
     * 分页
     *
     * @param page
     * @param shipmentDemandId
     * @return
     */
    Page<OutgoingScanVO> getOutgoingScanListPageList(Page<OutgoingScanVO> page, Integer shipmentDemandId);

    /**
     * 保存出货扫描列表
     *
     * @param shipmentScanListDTO
     */
    void save(ShipmentScanListDTO shipmentScanListDTO, UserBO userBO);

    void batchSave(List<ShipmentScanListDTO> shipmentScanListDTOList, UserBO userBO);
}
