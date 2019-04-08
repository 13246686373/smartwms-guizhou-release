package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.WarehouseReconciliation;
import com.frdscm.wms.entity.dto.WarehouseOrderDTO;
import com.frdscm.wms.entity.dto.WarehouseReconciliationDTO;
import com.frdscm.wms.entity.dto.WarehouseReconciliationPageDTO;
import com.frdscm.wms.entity.vo.WarehouseReconciliationVO;
import com.frdscm.wms.mapper.ExpenseItemMapper;
import com.frdscm.wms.mapper.WarehouseReconciliationMapper;
import com.frdscm.wms.service.IWarehouseReconciliationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dizhang
 * @since 2019-04-06
 */
@Service
public class WarehouseReconciliationServiceImpl extends ServiceImpl<WarehouseReconciliationMapper, WarehouseReconciliation> implements IWarehouseReconciliationService {

    @Autowired
    public WarehouseReconciliationMapper reconciliationMapper;

    @Autowired
    public ExpenseItemMapper expenseItemMapper;

    @Override
    public WarehouseReconciliation save(WarehouseReconciliationDTO reconciliationDTO) {

        WarehouseReconciliation reconciliation = BeanUtils.copy(reconciliationDTO, WarehouseReconciliation.class);

        Integer count = reconciliationMapper.selectCount();
        count += 1;
        String str = String.format("%04d", count);
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String date = dateFormat.format(new Date());
        reconciliation.setReconciliationNo("RC" + date + str);

        super.insert(reconciliation);

        return reconciliation;
    }

    @Override
    public void updateStatus(List<Integer> idList, Integer status) {
        reconciliationMapper.updateStatusById(idList, status);
    }

    @Override
    public Page<WarehouseReconciliationVO> selectByPage(Page<WarehouseReconciliationVO> page, WarehouseReconciliationPageDTO reconciliationPageDTO) {
        List<WarehouseReconciliationVO> reconciliationVOList = reconciliationMapper.selectByPageList(page, reconciliationPageDTO);
        // 计算订单下的费用项总计
        for (WarehouseReconciliationVO reconciliationVO : reconciliationVOList) {
            String orderNo = reconciliationVO.getOrderNo();
            reconciliationVO.setOrderExpenseItemTotal(expenseItemMapper.getOrderExpenseItemTotal(orderNo));
        }
        return page.setRecords(reconciliationVOList);
    }
}
