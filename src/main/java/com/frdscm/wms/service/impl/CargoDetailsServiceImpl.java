package com.frdscm.wms.service.impl;

import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.CargoDetails;
import com.frdscm.wms.entity.WarehouseOrder;
import com.frdscm.wms.entity.dto.CargoDetailsDTO;
import com.frdscm.wms.mapper.CargoDetailsMapper;
import com.frdscm.wms.service.ICargoDetailsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dizhang
 * @since 2019-04-05
 */
@Service
public class CargoDetailsServiceImpl extends ServiceImpl<CargoDetailsMapper, CargoDetails> implements ICargoDetailsService {

    @Autowired
    public CargoDetailsMapper cargoDetailsMapper;

    @Override
    public void batchSave(WarehouseOrder warehouseOrder, List<CargoDetailsDTO> cargoDetailsDTOList) {

        if (CollectionUtils.isEmpty(cargoDetailsDTOList)) {
            return ;
        }

        List<CargoDetails> cargoDetailsList = new ArrayList<>();

        for (CargoDetailsDTO cargoDetailsDTO : cargoDetailsDTOList) {
            CargoDetails cargoDetails = BeanUtils.copy(cargoDetailsDTO, CargoDetails.class);
            cargoDetails.setOrderId(warehouseOrder.getId());
            cargoDetails.setOrderNo(warehouseOrder.getOrderNo());
            cargoDetails.setEnabledFlag("Y");
            cargoDetailsList.add(cargoDetails);
        }

        super.insertBatch(cargoDetailsList);
    }

    @Override
    public void deleteDetailByOrderId(Integer warehouseOrderId) {
        cargoDetailsMapper.updateExpenseItem(warehouseOrderId);
    }

    @Override
    public List<CargoDetailsDTO> getCargoDetailsByOrderId(Integer orderId) {
        return cargoDetailsMapper.getCargoDetailsByOrderId(orderId);
    }
}
