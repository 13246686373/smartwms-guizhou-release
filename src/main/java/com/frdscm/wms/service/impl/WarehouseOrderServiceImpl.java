package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ExpenseItem;
import com.frdscm.wms.entity.WarehouseOrder;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.WarehouseOrderVO;
import com.frdscm.wms.mapper.ReceiptDemandMapper;
import com.frdscm.wms.mapper.WarehouseOrderMapper;
import com.frdscm.wms.mapper.WarehouseReconciliationMapper;
import com.frdscm.wms.service.*;
import com.frdscm.wms.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class WarehouseOrderServiceImpl extends ServiceImpl<WarehouseOrderMapper, WarehouseOrder> implements IWarehouseOrderService {

    @Autowired
    public ICargoDetailsService cargoDetailsService;

    @Autowired
    public IExpenseItemService expenseItemService;

    @Autowired
    public WarehouseOrderMapper warehouseOrderMapper;

    @Autowired
    public ReceiptDemandMapper receiptDemandMapper;

    @Autowired
    public IReceiptDemandService receiptDemandService;

    @Autowired
    public IWarehouseReconciliationService warehouseReconciliationService;

    @Autowired
    public WarehouseReconciliationMapper warehouseReconciliationMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public WarehouseOrder save(WarehouseOrderDTO warehouseOrderDTO, UserBO userBO) {

        warehouseOrderDTO.setOperatorId(userBO.getUserId());
        warehouseOrderDTO.setOperatorName(userBO.getUserName());

        // 1. 判断订单ID是否存在, 如果存在, 则先删再添加
        WarehouseOrder order = super.selectById(warehouseOrderDTO.getId());
        if (null != order) {
            // 调用删除接口
            warehouseOrderMapper.updateEnabledFlag(order.getId());
            cargoDetailsService.deleteDetailByOrderId(order.getId());
            expenseItemService.deleteExpenseItemByOrderId(order.getId());
        } else {
            saveReceiptDemand(warehouseOrderDTO, userBO);

            saveReconciliation(warehouseOrderDTO, userBO);

            if (StringUtils.isEmpty(warehouseOrderDTO.getOrderNo())) {
                Integer count = warehouseOrderMapper.selectCount();
                count += 1;
                String str = String.format("%05d", count);
                DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
                String date = dateFormat.format(new Date());
                warehouseOrderDTO.setOrderNo("OR" + date + str);
            }
        }

        return saveWarehouseOrder(warehouseOrderDTO);
    }

    /**
     * 创建订单同步到收货需求
     * @param warehouseOrderDTO
     * @param userBO
     */
    private void saveReceiptDemand(WarehouseOrderDTO warehouseOrderDTO, UserBO userBO) {
        // 构造实体
        ReceiptDemandDTO receiptDemandDTO = new ReceiptDemandDTO();
        receiptDemandDTO.setWarehouseId(warehouseOrderDTO.getWarehouseId());
        receiptDemandDTO.setTypeId(179);
        receiptDemandDTO.setTypeName("正常收货");

        // 判断收货方式  1-普通入库  2-仓单质押  3-电商下单
        if (1 == warehouseOrderDTO.getOrderType()) {
            receiptDemandDTO.setModeId(174);
            receiptDemandDTO.setModeName("仓库下单");
        } else if (2 == warehouseOrderDTO.getOrderType()) {
            receiptDemandDTO.setModeId(314);
            receiptDemandDTO.setModeName("仓单质押");
        } else if (3 == warehouseOrderDTO.getOrderType()) {
            receiptDemandDTO.setModeId(313);
            receiptDemandDTO.setModeName("电商下单");
        }

        receiptDemandDTO.setOrderNumber(warehouseOrderDTO.getOrderNo());
        receiptDemandDTO.setExpectedTime(warehouseOrderDTO.getRojectedToWarehouseTime());
        receiptDemandDTO.setOperatingModeId(176);
        receiptDemandDTO.setOperatingModeName("入仓入储");

        //生成运单号 格式 R+yyMMdd+0001
        int count = receiptDemandMapper.countSingleNumber("R" + DateUtil.dayNow());
        receiptDemandDTO.setSingleNumber("R" + DateUtil.dayNow() + String.format("%03d", count));

        receiptDemandDTO.setClientId(warehouseOrderDTO.getClientId());
        receiptDemandDTO.setClientName(warehouseOrderDTO.getClientName());

        List<ReceiptCargoDetailsDTO> receiptCargoDetailsDTOList = new ArrayList<>();

        for (CargoDetailsDTO cargoDetailsDTO : warehouseOrderDTO.getCargoDetailList()) {
            ReceiptCargoDetailsDTO receiptCargoDetailsDTO = new ReceiptCargoDetailsDTO();
            receiptCargoDetailsDTO.setMaterialNumber(cargoDetailsDTO.getMaterialsName());
            receiptCargoDetailsDTO.setMaterialSpecifications(cargoDetailsDTO.getSpecificationGrade());
            receiptCargoDetailsDTO.setVolume(cargoDetailsDTO.getVolume());
            receiptCargoDetailsDTO.setUnitId(cargoDetailsDTO.getUnitId());
            receiptCargoDetailsDTO.setUnit(cargoDetailsDTO.getUnit());
            receiptCargoDetailsDTO.setBatchNumber(cargoDetailsDTO.getBatchNo());
        }

        receiptDemandDTO.setReceiptCargoDetailsList(receiptCargoDetailsDTOList);

        receiptDemandService.save(receiptDemandDTO, userBO);
    }

    /**
     * 创建订单同步到应收账单
     * @param warehouseOrderDTO
     * @param userBO
     */
    private void saveReconciliation(WarehouseOrderDTO warehouseOrderDTO, UserBO userBO) {
        // 构造实体
        WarehouseReconciliationDTO reconciliationDTO = new WarehouseReconciliationDTO();
        reconciliationDTO.setOrderId(warehouseOrderDTO.getId());
        reconciliationDTO.setOrderNo(warehouseOrderDTO.getOrderNo());
        reconciliationDTO.setClientId(warehouseOrderDTO.getClientId());
        reconciliationDTO.setClientName(warehouseOrderDTO.getClientName());
        reconciliationDTO.setContractNo(warehouseOrderDTO.getContractNo());
        reconciliationDTO.setCurrency("RMB");
        reconciliationDTO.setWarehouseId(warehouseOrderDTO.getWarehouseId());
        reconciliationDTO.setWarehouseName(warehouseOrderDTO.getReceivingWarehouse());
        reconciliationDTO.setStatus(0);
        reconciliationDTO.setEnabledFlag("Y");
        warehouseReconciliationService.save(reconciliationDTO);
    }

    private WarehouseOrder saveWarehouseOrder(WarehouseOrderDTO warehouseOrderDTO) {
        // 1.保存订单
        WarehouseOrder warehouseOrder = BeanUtils.copy(warehouseOrderDTO, WarehouseOrder.class);

        warehouseOrder.setEnabledFlag("Y");
        super.insert(warehouseOrder);

        warehouseOrder = warehouseOrderMapper.getWarehouseOrderByOrderNo(warehouseOrder.getOrderNo());

        // 2.保存货物明细
        cargoDetailsService.batchSave(warehouseOrder, warehouseOrderDTO.getCargoDetailList());

        // 3.保存费用项
        expenseItemService.batchSave(warehouseOrder, warehouseOrderDTO.getExpenseItemList());

        return warehouseOrder;
    }

    /**
     * 根据订单ID获取订单详情
     * @param orderId
     * @return
     */
    @Override
    public WarehouseOrderDTO getWarehouseOrderById(Integer orderId) {

        WarehouseOrder warehouse = super.selectById(orderId);
        if (null == warehouse) {
            throw new BusinessException("仓库订单信息不存在，请确认");
        }

        WarehouseOrder warehouseOrder = super.selectById(orderId);

        WarehouseOrderDTO warehouseOrderDTO = BeanUtils.copy(warehouseOrder, WarehouseOrderDTO.class);

        warehouseOrderDTO.setCargoDetailList(cargoDetailsService.getCargoDetailsByOrderId(orderId));

        warehouseOrderDTO.setExpenseItemList(expenseItemService.getExpenseItemByOrderId(orderId));

        return warehouseOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Integer id, String orderNo) {

        WarehouseOrder warehouse = super.selectById(id);
        if (null == warehouse) {
            throw new BusinessException("仓库订单信息不存在，请确认");
        }
        warehouseOrderMapper.updateEnabledFlag(id);

        cargoDetailsService.deleteDetailByOrderId(id);

        expenseItemService.deleteExpenseItemByOrderId(id);

        // 删除订单对应的收货需求
        receiptDemandMapper.deleteByOrderNo(orderNo);

        // 删除订单对账单
        warehouseReconciliationMapper.deleteByOrderNo(orderNo);

    }

    /**
     * 根据查询条件,分页查询订单列表
     * @param page
     * @param warehouseOrderPageDTO
     * @return
     */
    @Override
    public Page<WarehouseOrderVO> selectByPage(Page<WarehouseOrderVO> page, WarehouseOrderPageDTO warehouseOrderPageDTO) {
        return page.setRecords(warehouseOrderMapper.selectByPageList(page, warehouseOrderPageDTO));
    }

}
