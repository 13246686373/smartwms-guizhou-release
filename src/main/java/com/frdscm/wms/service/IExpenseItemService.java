package com.frdscm.wms.service;

import com.frdscm.wms.entity.ExpenseItem;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.WarehouseOrder;
import com.frdscm.wms.entity.dto.CargoDetailsDTO;
import com.frdscm.wms.entity.dto.ExpenseItemDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dizhang
 * @since 2019-04-05
 */
public interface IExpenseItemService extends IService<ExpenseItem> {

    void batchSave(WarehouseOrder warehouseOrder, List<ExpenseItemDTO> expenseItemDTOList);

    void deleteExpenseItemByOrderId(Integer warehouseOrderId);

    List<ExpenseItemDTO> getExpenseItemByOrderId(Integer orderId);

}
