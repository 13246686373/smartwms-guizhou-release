package com.frdscm.wms.service;

import com.frdscm.wms.entity.CargoDetails;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.WarehouseOrder;
import com.frdscm.wms.entity.dto.CargoDetailsDTO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dizhang
 * @since 2019-04-05
 */
public interface ICargoDetailsService extends IService<CargoDetails> {

    void batchSave(WarehouseOrder warehouseOrder, List<CargoDetailsDTO> cargoDetailsDTOList);

    void deleteDetailByOrderId(Integer warehouseOrderId);

    List<CargoDetailsDTO> getCargoDetailsByOrderId(Integer orderId);

}
