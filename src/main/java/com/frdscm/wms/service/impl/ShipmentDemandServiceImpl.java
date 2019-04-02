package com.frdscm.wms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.ShipmentCargoDetailsDTO;
import com.frdscm.wms.entity.dto.ShipmentDemandDTO;
import com.frdscm.wms.entity.dto.ShipmentDemandPageDTO;
import com.frdscm.wms.entity.dto.UnitMappingDTO;
import com.frdscm.wms.entity.vo.OrderMaterialVo;
import com.frdscm.wms.entity.vo.OrderReceiverOrSenderVo;
import com.frdscm.wms.entity.vo.OrderVo;
import com.frdscm.wms.entity.vo.ShipmentDemandVO;
import com.frdscm.wms.feign.OrderService;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.*;
import com.frdscm.wms.service.IShipmentDemandService;
import com.frdscm.wms.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 出货需求表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Service
public class ShipmentDemandServiceImpl extends ServiceImpl<ShipmentDemandMapper, ShipmentDemand> implements IShipmentDemandService {

    private final Logger LOGGER = LoggerFactory.getLogger(ShipmentDemandServiceImpl.class);

    private final ShipmentDemandMapper shipmentDemandMapper;
    private final ShipmentCargoDetailsMapper shipmentCargoDetailsMapper;
    private final ShipmentListMapper shipmentListMapper;
    private final InventoryManageMapper inventoryManageMapper;
    private final WarehouseStorageMapper warehouseStorageMapper;
    private final ShipmentManageMapper shipmentManageMapper;
    private final ProjectService projectService;
    private final OrderService orderService;

    @Autowired
    public ShipmentDemandServiceImpl(ShipmentDemandMapper shipmentDemandMapper, ShipmentCargoDetailsMapper shipmentCargoDetailsMapper, ShipmentListMapper shipmentListMapper,
                                     InventoryManageMapper inventoryManageMapper, WarehouseStorageMapper warehouseStorageMapper, ShipmentManageMapper shipmentManageMapper,
                                     ProjectService projectService, OrderService orderService) {
        this.shipmentDemandMapper = shipmentDemandMapper;
        this.shipmentCargoDetailsMapper = shipmentCargoDetailsMapper;
        this.shipmentListMapper = shipmentListMapper;
        this.inventoryManageMapper = inventoryManageMapper;
        this.warehouseStorageMapper = warehouseStorageMapper;
        this.shipmentManageMapper = shipmentManageMapper;
        this.projectService = projectService;
        this.orderService = orderService;
    }

    @Override
    public ShipmentDemand selectShipmentDemandById(Integer id) {
        return super.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ShipmentDemandDTO save(ShipmentDemandDTO shipmentDemandDTO, UserBO userBO) {
        int count = super.baseMapper.countSingleNumber("S" + DateUtil.dayNow());
        String str = String.format("%03d", count);
        //生成出货单号 格式 S+yyMMdd+0001
        String sNum = "S" + DateUtil.dayNow() + str;
        ShipmentDemand shipmentDemand = BeanUtils.copy(shipmentDemandDTO, ShipmentDemand.class);
        shipmentDemand.setOperatorId(userBO.getUserId());
        shipmentDemand.setOperatorName(userBO.getUserName());
        shipmentDemand.setSingleNumber(sNum);
        super.insert(shipmentDemand);
        for (ShipmentCargoDetailsDTO shipmentCargoDetailsDTO : shipmentDemandDTO.getShipmentCargoDetailsList()) {
            ShipmentCargoDetails shipmentCargoDetails = BeanUtils.copy(shipmentCargoDetailsDTO, ShipmentCargoDetails.class);
            shipmentCargoDetails.setShipmentDemandId(shipmentDemand.getId());
            shipmentCargoDetailsMapper.insert(shipmentCargoDetails);
            shipmentCargoDetailsDTO.setId(shipmentCargoDetails.getId());
        }
        shipmentDemandDTO.setId(shipmentDemand.getId());
        shipmentDemandDTO.setSingleNumber(sNum);
        return shipmentDemandDTO;
    }

    /**
     * 保存订单
     *
     * @param shipmentDemandDTO
     */
    private void saveOrder(ShipmentDemand shipmentDemandDTO, UserBO userBO) throws UnsupportedEncodingException {
        OrderVo orderVo = new OrderVo();
        orderVo.setCustomerNumber(shipmentDemandDTO.getSingleNumber());
        orderVo.setClientId(shipmentDemandDTO.getClientId());
        orderVo.setClientShortName(shipmentDemandDTO.getClientName());
        orderVo.setClientName(shipmentDemandDTO.getClientName());
        orderVo.setLiftingModeId(159);
        orderVo.setLiftingModeName("一提一送");
        if (null != shipmentDemandDTO.getProjectId()) {
            orderVo.setProjectId(shipmentDemandDTO.getProjectId());
        }
        if (null != shipmentDemandDTO.getProjectName()) {
            orderVo.setProjectName(shipmentDemandDTO.getProjectName());
        }
        setOrderMaterialInfo(shipmentDemandDTO, orderVo);
        LOGGER.error("userBO: {}", JSON.toJSONString(userBO));
        //生成订单
        orderService.save(orderVo, userBO.getUserId(), URLEncoder.encode(userBO.getUserName(), "UTF-8"), userBO.getCompanyId());
    }

    /**
     * 设置收发货方信息
     *
     * @param shipmentDemandDTO
     */
    private void setOrderMaterialInfo(ShipmentDemand shipmentDemandDTO, OrderVo orderVo) {
        //设置发货方
        List<OrderReceiverOrSenderVo> senderVoList = new ArrayList<>();
        List<OrderReceiverOrSenderVo> receiverVoList = new ArrayList<>();
        List<OrderMaterialVo> materialVoList = setOrderMaterialInfo(shipmentDemandDTO.getId());

        OrderReceiverOrSenderVo senderVo = new OrderReceiverOrSenderVo();
        OrderReceiverOrSenderVo receiverVo = new OrderReceiverOrSenderVo();
        senderVo.setBid(shipmentDemandDTO.getWarehouseId());
        //出货仓库名称
        senderVo.setName(shipmentDemandDTO.getWarehouseName());
        senderVo.setReceiverOrSenderType(1);
        senderVo.setMaterialList(materialVoList);
        if (null != shipmentDemandDTO.getConsigneeId()) {
            receiverVo.setBid(shipmentDemandDTO.getConsigneeId());
        }
        if (null != shipmentDemandDTO.getConsigneeName()) {
            receiverVo.setName(shipmentDemandDTO.getConsigneeName());
        }
        if (null != shipmentDemandDTO.getReceiptAddress()) {
            receiverVo.setAddress(shipmentDemandDTO.getReceiptAddress());
            receiverVo.setDetailAddress(shipmentDemandDTO.getReceiptAddress());
        }
        receiverVo.setReceiverOrSenderType(2);
        receiverVo.setReceiveType("warehouse");
        receiverVo.setMaterialList(materialVoList);
        senderVoList.add(senderVo);
        receiverVoList.add(receiverVo);

        orderVo.setSenderList(senderVoList);
        orderVo.setReceiverList(receiverVoList);
    }

    /**
     * 设置订单物料信息
     *
     * @return
     */
    private List<OrderMaterialVo> setOrderMaterialInfo(Integer ShipmentDemandId) {
        List<OrderMaterialVo> orderMaterialVoList = new ArrayList<>();
        OrderMaterialVo orderMaterialVo;
        EntityWrapper<ShipmentCargoDetails> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("shipment_demand_id", ShipmentDemandId);
        List<ShipmentCargoDetails> shipmentCargoDetailsList = shipmentCargoDetailsMapper.selectList(entityWrapper);
        for (ShipmentCargoDetails shipmentCargoDetailsDTO : shipmentCargoDetailsList) {
            orderMaterialVo = BeanUtils.copy(shipmentCargoDetailsDTO, OrderMaterialVo.class);
            orderMaterialVo.setMaterialItemNumber(shipmentCargoDetailsDTO.getMaterialNumber());
            orderMaterialVo.setItemName(shipmentCargoDetailsDTO.getMaterialName());
            orderMaterialVo.setItemSpecifications(shipmentCargoDetailsDTO.getMaterialSpecifications());
            orderMaterialVo.setQuantity(shipmentCargoDetailsDTO.getQuantityCount());
            orderMaterialVo.setUnitName(shipmentCargoDetailsDTO.getUnit());
            orderMaterialVo.setBoxCountRule(shipmentCargoDetailsDTO.getBoxScale());
            orderMaterialVo.setQuantityRule(shipmentCargoDetailsDTO.getQuantityScale());
            orderMaterialVo.setGrossWeightRule(shipmentCargoDetailsDTO.getWeightScale());
            orderMaterialVo.setVolumeRule(shipmentCargoDetailsDTO.getVolumeScale());
            orderMaterialVoList.add(orderMaterialVo);
        }
        return orderMaterialVoList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ShipmentDemandDTO edit(ShipmentDemandDTO shipmentDemandDTO) {
        ShipmentDemand shipmentDemand = BeanUtils.copy(shipmentDemandDTO, ShipmentDemand.class);
        if (shipmentDemandDTO.getShipmentCargoDetailsList() != null) {
            ShipmentCargoDetails shipmentCargoDetails;
            for (ShipmentCargoDetailsDTO shipmentCargoDetailsDTO : shipmentDemandDTO.getShipmentCargoDetailsList()) {
                shipmentCargoDetails = BeanUtils.copy(shipmentCargoDetailsDTO, ShipmentCargoDetails.class);
                shipmentCargoDetails.setShipmentDemandId(shipmentDemandDTO.getId());
                if (shipmentCargoDetails.getId() == null) {
                    shipmentCargoDetailsMapper.insert(shipmentCargoDetails);
                } else {
                    shipmentCargoDetailsMapper.updateById(shipmentCargoDetails);
                }
                shipmentCargoDetailsDTO.setId(shipmentCargoDetails.getId());
            }
        }
        if (shipmentDemandDTO.getShipmentCargoDetailsIdDeleteList() != null) {
            for (Integer shipmentCargoDetailsId : shipmentDemandDTO.getShipmentCargoDetailsIdDeleteList()) {
                shipmentCargoDetailsMapper.deleteById(shipmentCargoDetailsId);
            }
        }
        super.updateById(shipmentDemand);
        return shipmentDemandDTO;
    }

    @Override
    public Page<ShipmentDemandVO> getShipmentDemandByPageList(Page<ShipmentDemandVO> page, ShipmentDemandPageDTO shipmentDemandPageDTO) {
        return page.setRecords(shipmentDemandMapper.getShipmentDemandByPageList(page, shipmentDemandPageDTO));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateStatus(Integer status, Integer shipmentDemandId) {
        ShipmentDemand shipmentDemand = super.selectById(shipmentDemandId);
        if (shipmentDemand.getStatus() != 1) {
            throw new BusinessException("操作失败");
        }
        List<ShipmentCargoDetails> shipmentCargoDetailsList = shipmentCargoDetailsMapper.getShipmentCargoDetailsListByShipmentDemandId(shipmentDemandId);
        Integer clientId = shipmentDemand.getClientId();
        if (status == 2) {
            ShipmentManage shipmentManage = new ShipmentManage();
            shipmentManage.setShipmentDemandId(shipmentDemandId);
            //设置待拣货
            shipmentManage.setStatus(1);
            shipmentManageMapper.insert(shipmentManage);
            //先进先出
            ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ra.getRequest();
            Integer companyId = Integer.valueOf(request.getHeader("companyId"));
            if (shipmentDemand.getPickTypeName().equals("先进先出")) {
                for (ShipmentCargoDetails shipmentCargoDetails : shipmentCargoDetailsList) {
                    UnitMappingDTO unitMappingDTO = projectService.getBillUnitByUnitId(shipmentCargoDetails.getUnitId(), companyId);
                    List<InventoryManage> inventoryManageList = inventoryManageMapper.getInventoryManageByMNumberAndCid(shipmentCargoDetails.getMaterialNumber(), clientId, shipmentDemand.getWarehouseId());
                    generateShipmentList(shipmentDemandId, shipmentCargoDetails, inventoryManageList, unitMappingDTO);
                }
            }
            //按批次 拣货
            if (!shipmentDemand.getPickTypeName().equals("先进先出") && !shipmentDemand.getPickTypeName().equals("随机捡货")) {
                for (ShipmentCargoDetails shipmentCargoDetails : shipmentCargoDetailsList) {
                    //需要发货的数量
//                    String batchNumber = shipmentDemand.getBatchNumber();
//                    List<InventoryManage> inventoryManageList = inventoryManageMapper.getInventoryManageByMNumberAndCidAndBMumber(shipmentCargoDetails.getMaterialNumber(), clientId, batchNumber, shipmentDemand.getWarehouseId());
//                    UnitMappingDTO unitMappingDTO = projectService.getBillUnitByUnitId(shipmentCargoDetails.getUnitId(), companyId);
//                    generateShipmentList(shipmentDemandId, shipmentCargoDetails, inventoryManageList, unitMappingDTO);
                }
            }
//            OutgoingScanManage outgoingScanManage = new OutgoingScanManage();
//            outgoingScanManage.setIsBoxNum(2);
//            outgoingScanManage.setIsBoxSonNum(2);
//            outgoingScanManage.setIsSerialNum(2);
//            outgoingScanManage.setIsBoardNum(1);
//            outgoingScanManage.setIsScan(1);
//            outgoingScanManage.setShipmentManageId(shipmentManage.getId());
//            outgoingScanManageMapper.insert(outgoingScanManage);
        } else {
            ShipmentManage shipmentManage = shipmentManageMapper.selectShipmentManageByShipmentDemandId(shipmentDemandId);
            shipmentListMapper.deleteByShipmentManageId(shipmentManage.getId());
            shipmentManageMapper.deleteById(shipmentManage.getId());
//            EntityWrapper<OutgoingScanManage> ew = new EntityWrapper<>();
//            ew.eq("shipment_manage_id", shipmentManage.getId());
//            outgoingScanManageMapper.delete(ew);
        }
        shipmentDemandMapper.updateStatus(status, shipmentDemandId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void confirmDemand(ShipmentDemand shipmentDemand, UserBO userBO) {
        ShipmentManage shipmentManage = new ShipmentManage();
        shipmentManage.setShipmentDemandId(shipmentDemand.getId());
        shipmentManage.setStatus(1);
        shipmentManageMapper.insert(shipmentManage);
        if ("送货上门".equals(shipmentDemand.getShipmentMethodName())) {
            try {
                saveOrder(shipmentDemand, userBO);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        shipmentDemand.setStatus(2);
        super.updateById(shipmentDemand);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void cancelDemand(ShipmentDemand shipmentDemand, ShipmentManage shipmentManage) {
        shipmentListMapper.deleteByShipmentManageId(shipmentManage.getId());
        shipmentManageMapper.deleteShipmentManageByShipmentDemandId(shipmentManage.getShipmentDemandId());
//        EntityWrapper<OutgoingScanManage> ew = new EntityWrapper<>();
//        ew.eq("shipment_manage_id", shipmentManage.getId());
//        outgoingScanManageMapper.delete(ew);
        shipmentDemand.setStatus(1);
        super.updateById(shipmentDemand);
    }

    private void generateShipmentList(Integer shipmentDemandId, ShipmentCargoDetails shipmentCargoDetails, List<InventoryManage> inventoryManageList, UnitMappingDTO unitMappingDTO) {
        if (unitMappingDTO.getIsQuantityCount() == 1) {
            inventoryManageListFor(inventoryManageList, shipmentCargoDetails.getQuantityCount(), shipmentDemandId, shipmentCargoDetails);
        } else if (unitMappingDTO.getIsBoxCount() == 1) {
            inventoryManageListFor(inventoryManageList, shipmentCargoDetails.getBoxCount(), shipmentDemandId, shipmentCargoDetails);
        } else if (unitMappingDTO.getIsGrossWeight() == 1) {
            inventoryManageListFor(inventoryManageList, shipmentCargoDetails.getGrossWeight(), shipmentDemandId, shipmentCargoDetails);
        } else if (unitMappingDTO.getIsVolumeReceipt() == 1) {
            inventoryManageListFor(inventoryManageList, shipmentCargoDetails.getVolume(), shipmentDemandId, shipmentCargoDetails);
        }
    }

    @Override
    public void deleteByShipmentDemandId(Integer id) {
//        ShipmentManage shipmentManage = shipmentManageMapper.selectShipmentManageByShipmentDemandId(id);
//        if (shipmentManage != null) {
//            shipmentListMapper.deleteByShipmentManageId(shipmentManage.getId());
//            shipmentManageMapper.deleteById(shipmentManage.getId());
//        }
//        shipmentDemandMapper.updateStatus(-1, id);
        ShipmentDemand shipmentDemand = super.selectById(id);
        if (shipmentDemand == null) {
            throw new BusinessException("出货需求不存在，请确认");
        }
        if (shipmentDemand.getStatus() != 1) {
            throw new BusinessException("出货需求已确认无法删除");
        }
        super.deleteById(id);
    }

    @Override
    public List<ShipmentDemand> getShipmentDemandByTaskList(Integer warehouseId) {
        return shipmentDemandMapper.getShipmentDemandByTaskList(warehouseId);
    }


    private void inventoryManageListFor(List<InventoryManage> inventoryManageList, Object scdCount, int shipmentDemandId, ShipmentCargoDetails shipmentCargoDetails) {

        //剩余的数量
        int sCount = 0;
        //已需要出货的量
//        int spCount;
        BigDecimal spCount1;
        BigDecimal sCount1 = BigDecimal.valueOf(0);
        boolean isTrue = false;
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Integer companyId = Integer.valueOf(request.getHeader("companyId"));
        for (InventoryManage inventoryManage : inventoryManageList) {
            UnitMappingDTO unitMappingDTO = projectService.getBillUnitByUnitId(shipmentCargoDetails.getUnitId(), companyId);
            ShipmentList sl = null;
            if (unitMappingDTO.getIsQuantityCount() == 1) {
                //  scdCount  45
//                sCount += inventoryManage.getQuantityCount();
                //sCount
//                spCount = inventoryManage.getQuantityCount();

//                spCount = Integer.parseInt(scdCount.toString()) - (sCount - inventoryManage.getQuantityCount());
                if (sCount > Integer.parseInt(scdCount.toString())) {
//                    spCount = Integer.parseInt(scdCount.toString()) - (sCount - inventoryManage.getQuantityCount());
                    isTrue = true;
                }
//                sl = shipmentList(inventoryManage.getBoardNumber(), inventoryManage.getBatchNumber(), inventoryManage.getMaterialNumber(),
//                        inventoryManage.getMaterialName(), inventoryManage.getWarehouseStorageNumber(), inventoryManage.getBoxCount(),
//                        inventoryManage.getQuantityCount(), inventoryManage.getGrossWeight(), inventoryManage.getNetWeight(),
//                        inventoryManage.getVolume(), shipmentDemandId, inventoryManage.getUnit(),
//                        inventoryManage.getUnitId());

            } else if (unitMappingDTO.getIsBoxCount() == 1) {
//                sCount += inventoryManage.getBoxCount();
//                spCount = inventoryManage.getBoxCount();
//                spCount = Integer.parseInt(scdCount.toString()) - (sCount - inventoryManage.getBoxCount());
                if (sCount > Integer.parseInt(scdCount.toString())) {
//                    spCount = Integer.parseInt(scdCount.toString()) - (sCount - inventoryManage.getBoxCount());
                    isTrue = true;
                }
//                sl = shipmentList(inventoryManage.getBoardNumber(), inventoryManage.getBatchNumber(), inventoryManage.getMaterialNumber(),
//                        inventoryManage.getMaterialName(), inventoryManage.getWarehouseStorageNumber(), inventoryManage.getBoxCount(),
//                        inventoryManage.getQuantityCount(), inventoryManage.getGrossWeight(), inventoryManage.getNetWeight(),
//                        inventoryManage.getVolume(), shipmentDemandId, inventoryManage.getUnit(),
//                        inventoryManage.getUnitId());
            } else if (unitMappingDTO.getIsVolumeReceipt() == 1) {
                sCount1 = BigDecimal.valueOf(0);
//                sCount1 = inventoryManage.getVolume().add(sCount1);
//                spCount1 = inventoryManage.getVolume();
                BigDecimal objScdCount = (BigDecimal) scdCount;
//                spCount1 = objScdCount.subtract(sCount1.subtract(inventoryManage.getVolume()));
                if (sCount1.compareTo(objScdCount) < 0) {
//                    spCount1 = objScdCount.subtract(sCount1.subtract(inventoryManage.getVolume()));
                    isTrue = true;
                }
//                sl = shipmentList(inventoryManage.getBoardNumber(), inventoryManage.getBatchNumber(), inventoryManage.getMaterialNumber(),
//                        inventoryManage.getMaterialName(), inventoryManage.getWarehouseStorageNumber(), inventoryManage.getBoxCount(),
//                        inventoryManage.getQuantityCount(), inventoryManage.getGrossWeight(), inventoryManage.getNetWeight(),
//                        inventoryManage.getVolume(), shipmentDemandId, inventoryManage.getUnit(),
//                        inventoryManage.getUnitId());
//
            } else if (unitMappingDTO.getIsGrossWeight() == 1) {

//                sCount1 = inventoryManage.getGrossWeight().add(sCount1);
//                spCount1 = inventoryManage.getGrossWeight();
                BigDecimal objScdCount = (BigDecimal) scdCount;
//                spCount1 = objScdCount.subtract(sCount1.subtract(inventoryManage.getGrossWeight()));
                if (sCount1.compareTo(objScdCount) < 0) {
//                    spCount1 = objScdCount.subtract(sCount1.subtract(inventoryManage.getGrossWeight()));
                    isTrue = true;
                }
//                sl = shipmentList(inventoryManage.getBoardNumber(), inventoryManage.getBatchNumber(), inventoryManage.getMaterialNumber(),
//                        inventoryManage.getMaterialName(), inventoryManage.getWarehouseStorageNumber(), inventoryManage.getBoxCount(),
//                        inventoryManage.getQuantityCount(), inventoryManage.getGrossWeight(), inventoryManage.getNetWeight(),
//                        inventoryManage.getVolume(), shipmentDemandId, inventoryManage.getUnit(),
//                        inventoryManage.getUnitId());
            }
            shipmentListMapper.insert(sl);
            if (isTrue) {
                break;
            }
        }
    }

    public ShipmentList shipmentList(String boardNumber, String batchNumber, String materialNumber,
                                     String materialName, String warehouseStorageNumber, Integer boxCount,
                                     Integer quantityCount, BigDecimal grossWeight, BigDecimal netWeight,
                                     BigDecimal volume, Integer shipmentDemandId, String unit, Integer unitId
    ) {
        ShipmentList sl = new ShipmentList();
        sl.setBoardNumber(boardNumber);
//        sl.setBatchNumber(batchNumber);
//        sl.setMaterialNumber(materialNumber);
//        sl.setMaterialName(materialName);
//        sl.setWarehouseStorageId(warehouseStorageMapper.getIdByNumber(warehouseStorageNumber));
//        sl.setBoxCount(boxCount);
//        sl.setQuantityCount(quantityCount);
//        sl.setGrossWeight(grossWeight);
//        sl.setNetWeight(netWeight);
//        sl.setVolume(volume);
//        sl.setUnit(unit);
//        sl.setUnitId(unitId);
//        sl.setShipmentManageId(shipmentManageMapper.selectShipmentManageByShipmentDemandId(shipmentDemandId).getId());
        return sl;
    }
}


