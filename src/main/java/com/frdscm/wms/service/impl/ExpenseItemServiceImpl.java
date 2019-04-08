package com.frdscm.wms.service.impl;

import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.CargoDetails;
import com.frdscm.wms.entity.ExpenseItem;
import com.frdscm.wms.entity.WarehouseOrder;
import com.frdscm.wms.entity.dto.CargoDetailsDTO;
import com.frdscm.wms.entity.dto.ExpenseItemDTO;
import com.frdscm.wms.mapper.ExpenseItemMapper;
import com.frdscm.wms.service.IExpenseItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ExpenseItemServiceImpl extends ServiceImpl<ExpenseItemMapper, ExpenseItem> implements IExpenseItemService {

    @Autowired
    public ExpenseItemMapper  expenseItemMapper;

    @Override
    public void batchSave(WarehouseOrder warehouseOrder, List<ExpenseItemDTO> expenseItemDTOList) {
        if (CollectionUtils.isEmpty(expenseItemDTOList)) {
            return ;
        }

        List<ExpenseItem> expenseItemList = new ArrayList<>();

        for (ExpenseItemDTO expenseItemDTO : expenseItemDTOList) {
            ExpenseItem expenseItem = BeanUtils.copy(expenseItemDTO, ExpenseItem.class);
            expenseItem.setOrderId(warehouseOrder.getId());
            expenseItem.setOrderNo(warehouseOrder.getOrderNo());

            // 计算单个费用项价格
            BigDecimal orderKeyData = new BigDecimal(expenseItem.getOrderKeyData());
            BigDecimal expenseItemTotal = orderKeyData.multiply(expenseItem.getUnitPrice());

            expenseItem.setExpenseItemTotal(expenseItemTotal);
            expenseItem.setEnabledFlag("Y");
            expenseItemList.add(expenseItem);
        }

        super.insertBatch(expenseItemList);
    }

    /**
     * 逻辑删除 将指定订单下的费用项置为失效
     * @param warehouseOrderId
     */
    @Override
    public void deleteExpenseItemByOrderId(Integer warehouseOrderId) {
        expenseItemMapper.updateExpenseItem(warehouseOrderId);
    }

    @Override
    public List<ExpenseItemDTO> getExpenseItemByOrderId(Integer orderId) {
        return expenseItemMapper.getExpenseItemByOrderId(orderId);
    }
}
