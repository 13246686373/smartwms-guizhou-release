package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.entity.WarehouseReconciliation;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.dto.WarehouseReconciliationDTO;
import com.frdscm.wms.entity.dto.WarehouseReconciliationPageDTO;
import com.frdscm.wms.entity.vo.WarehouseReconciliationVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dizhang
 * @since 2019-04-06
 */
public interface IWarehouseReconciliationService extends IService<WarehouseReconciliation> {

    WarehouseReconciliation save(WarehouseReconciliationDTO reconciliationDTO);

    void updateStatus(List<Integer> idList, Integer status);

    Page<WarehouseReconciliationVO> selectByPage(Page<WarehouseReconciliationVO> reconciliationVO, WarehouseReconciliationPageDTO reconciliationPageDTO);

}
