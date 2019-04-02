package com.frdscm.wms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ReceiptDemand;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.ReceiptManage;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.ReceiptCargoDetailsDTO;
import com.frdscm.wms.entity.dto.ReceiptDemandDTO;
import com.frdscm.wms.entity.dto.ReceiptDemandPageDTO;
import com.frdscm.wms.entity.dto.UnitMappingDTO;
import com.frdscm.wms.entity.vo.ReceiptDemandAppVO;
import com.frdscm.wms.entity.vo.ReceiptDemandVO;
import com.frdscm.wms.entity.vo.ReceiptListVO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.ReceiptCargoDetailsMapper;
import com.frdscm.wms.mapper.ReceiptDemandMapper;
import com.frdscm.wms.mapper.ReceiptListMapper;
import com.frdscm.wms.mapper.ReceiptManageMapper;
import com.frdscm.wms.service.IReceiptDemandService;
import com.frdscm.wms.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 收货需求表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Service
public class ReceiptDemandServiceImpl extends ServiceImpl<ReceiptDemandMapper, ReceiptDemand> implements IReceiptDemandService {
    private final Logger LOGGER = LoggerFactory.getLogger(ReceiptDemandServiceImpl.class);

    private final ReceiptCargoDetailsMapper receiptCargoDetailsMapper;
    private final ReceiptDemandMapper receiptDemandMapper;
    private final ReceiptManageMapper receiptManageMapper;
    private final ReceiptListMapper receiptListMapper;
    private final ProjectService projectService;

    @Autowired
    public ReceiptDemandServiceImpl(ReceiptCargoDetailsMapper receiptCargoDetailsMapper, ReceiptDemandMapper receiptDemandMapper, ReceiptManageMapper receiptManageMapper, ReceiptListMapper receiptListMapper, ProjectService projectService) {
        this.receiptCargoDetailsMapper = receiptCargoDetailsMapper;
        this.receiptDemandMapper = receiptDemandMapper;
        this.receiptManageMapper = receiptManageMapper;
        this.receiptListMapper = receiptListMapper;
        this.projectService = projectService;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReceiptDemandDTO save(ReceiptDemandDTO receiptDemandDTO, UserBO userBO) {
        int count = super.baseMapper.countSingleNumber("R" + DateUtil.dayNow());
        String str = String.format("%03d", count);
        //生成运单号 格式 R+yyMMdd+0001
        String sNum = "R" + DateUtil.dayNow() + str;
        ReceiptDemand receiptDemand = BeanUtils.copy(receiptDemandDTO, ReceiptDemand.class);
        receiptDemand.setOperatorId(userBO.getUserId());
        receiptDemand.setOperatorName(userBO.getUserName());
        receiptDemand.setSingleNumber(sNum);
        super.insert(receiptDemand);
        // 循环保存详细信息
        if (receiptDemandDTO.getReceiptCargoDetailsList() != null) {
            ReceiptCargoDetails receiptCargoDetails;
            for (ReceiptCargoDetailsDTO receiptCargoDetailsDTO : receiptDemandDTO.getReceiptCargoDetailsList()) {
                receiptCargoDetails = BeanUtils.copy(receiptCargoDetailsDTO, ReceiptCargoDetails.class);
                receiptCargoDetails.setReceiptDemandId(receiptDemand.getId());
                receiptCargoDetailsMapper.insert(receiptCargoDetails);
                receiptCargoDetailsDTO.setId(receiptCargoDetails.getId());
            }
        }
        //java -Denv=UAT -Dapp.id=smartscm
        //        -Dapp.ip=192.168.48.24
        //    -Deureka.client.serviceUrl.defaultZone=http://192.168.55.106:8761/eureka/
        // -Dkafka.broker.address=192.168.55.106:9092,192.168.48.24:9092,192.168.50.192:9092
        // -Dspring.datasource.url=jdbc:mysql://192.168.50.192/frdwms?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false
        // -Dspring.datasource.password=A123!@#
        // -Dspring.redis.host=192.168.50.192
        // -Dserver.port=8091 -Dapp.eureka.enabled=true
        // -Deureka.host=172.18.70.166 -jar wms-1.0.0.jar
        receiptDemandDTO.setId(receiptDemand.getId());
        receiptDemandDTO.setSingleNumber(sNum);
        return receiptDemandDTO;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReceiptDemandDTO edit(ReceiptDemandDTO receiptDemandDTO) {
        ReceiptDemand receiptDemand = BeanUtils.copy(receiptDemandDTO, ReceiptDemand.class);
        if (receiptDemandDTO.getReceiptCargoDetailsList() != null) {
            ReceiptCargoDetails receiptCargoDetails;
            for (ReceiptCargoDetailsDTO receiptCargoDetailsDTO : receiptDemandDTO.getReceiptCargoDetailsList()) {
                receiptCargoDetails = BeanUtils.copy(receiptCargoDetailsDTO, ReceiptCargoDetails.class);
                receiptCargoDetails.setReceiptDemandId(receiptDemand.getId());
                if (receiptCargoDetails.getId() == null) {
                    receiptCargoDetailsMapper.insert(receiptCargoDetails);
                } else {
                    receiptCargoDetailsMapper.updateById(receiptCargoDetails);
                }
                receiptCargoDetailsDTO.setId(receiptCargoDetails.getId());
            }
        }
        if (receiptDemandDTO.getReceiptCargoDetailsIdDeleteList() != null) {
            for (Integer receiptCargoDetailsId : receiptDemandDTO.getReceiptCargoDetailsIdDeleteList()) {
                receiptCargoDetailsMapper.deleteById(receiptCargoDetailsId);
            }
        }
        super.updateById(receiptDemand);
        return receiptDemandDTO;
    }


    private void getReceiptCargoDetails(ReceiptCargoDetails receiptCargoDetails, Integer companyId) {
        UnitMappingDTO unitMappingDTO = projectService.getBillUnitByUnitId(receiptCargoDetails.getUnitId(), companyId);
        LOGGER.error("unitMappingDTO: {}", JSON.toJSONString(unitMappingDTO));
        if (unitMappingDTO.getIsQuantityCount() == 1) {
            receiptCargoDetails.setUnitType(1);
        } else if (unitMappingDTO.getIsBoxCount() == 1) {
            receiptCargoDetails.setUnitType(2);
        } else if (unitMappingDTO.getIsBoard() == 1) {
            receiptCargoDetails.setUnitType(3);
        } else if (unitMappingDTO.getIsGrossWeight() == 1) {
            receiptCargoDetails.setUnitType(4);
        } else if (unitMappingDTO.getIsVolumeReceipt() == 1) {
            receiptCargoDetails.setUnitType(5);
        } else {
            throw new BusinessException("单位 " + receiptCargoDetails.getUnit() + " 无法找到收发货单位类型");
        }
    }

    @Override
    public void delete(Integer id) {
        ReceiptDemand receiptDemand = super.selectById(id);
        if (receiptDemand == null) {
            throw new BusinessException("收货需求不存在，请确认");
        }
        if (receiptDemand.getStatus() != 1) {
            throw new BusinessException("收货需求已确认无法删除");
        }
        super.deleteById(id);
    }

    @Override
    public Page<ReceiptDemandVO> getReceiptCargoDetailsList(Page<ReceiptDemandVO> page, ReceiptDemandPageDTO receiptDemandPageDTO) {
        return page.setRecords(receiptDemandMapper.getReceiptCargoDetailsList(page, receiptDemandPageDTO));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateStatus(Integer status, Integer id) {
        ReceiptList receiptList = new ReceiptList();
        if (status == 2) {
            ReceiptManage receiptManage = new ReceiptManage();
            receiptManage.setReceiptDemandId(id);
            receiptManage.setStatus(1);
//            receiptManage.setIsMeasureReceipt(0);
//            receiptManage.setIsBoxCount(0);
//            receiptManage.setIsQuantityCount(0);
//            receiptManage.setIsWeightReceipt(0);
//            receiptManage.setIsGrossWeight(0);
//            receiptManage.setIsNetWeight(0);
//            receiptManage.setIsVolumeReceipt(0);
//            receiptManage.setIsBatchReceipt(0);
            receiptManageMapper.insert(receiptManage);
            List<ReceiptCargoDetails> receiptCargoDetailsList = receiptCargoDetailsMapper.getReceiptCargoDetailsByReceiptDemandId(id);
            if (null != receiptCargoDetailsList) {
                for (ReceiptCargoDetails receiptCargoDetails : receiptCargoDetailsList) {
//                    receiptList.setBatchNumber(receiptCargoDetails.getBatchNumber());
//                    receiptList.setMaterialNumber(receiptCargoDetails.getMaterialNumber());
//                    receiptList.setMaterialName(receiptCargoDetails.getMaterialName());
//                    receiptList.setBoxCount(receiptCargoDetails.getBoxCount());
//                    receiptList.setQuantityCount(receiptCargoDetails.getQuantityCount());
//                    receiptList.setGrossWeight(receiptCargoDetails.getGrossWeight());
//                    receiptList.setNetWeight(receiptCargoDetails.getNetWeight());
//                    receiptList.setVolume(receiptCargoDetails.getVolume());
//                    receiptList.setBoardCount(receiptCargoDetails.getBoardCount());
//                    receiptList.setReceiptManageId(receiptManage.getId());
//                    receiptList.setUnit(receiptCargoDetails.getUnit());
//                    receiptList.setUnitId(receiptCargoDetails.getUnitId());
                    receiptList.setStatus(1);
                    //receiptListMapper.insert(receiptList);
                }
            }
        }
        receiptDemandMapper.updateStatus(status, id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void confirmDemand(ReceiptDemand receiptDemand, Integer companyId) {
        List<ReceiptCargoDetails> receiptCargoDetailsList = receiptCargoDetailsMapper.getReceiptCargoDetailsByReceiptDemandId(receiptDemand.getId());
        for (ReceiptCargoDetails receiptCargoDetails : receiptCargoDetailsList) {
            getReceiptCargoDetails(receiptCargoDetails, companyId);
            receiptCargoDetailsMapper.updateById(receiptCargoDetails);
        }
        ReceiptManage receiptManage = new ReceiptManage();
        receiptManage.setReceiptDemandId(receiptDemand.getId());
        receiptManageMapper.insert(receiptManage);
        receiptDemand.setStatus(2);
        super.updateById(receiptDemand);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void cancelDemand(ReceiptDemand receiptDemand) {
        EntityWrapper<ReceiptManage> ew = new EntityWrapper<>();
        ew.eq("receipt_demand_id", receiptDemand.getId());
        receiptManageMapper.delete(ew);
        receiptDemand.setStatus(1);
        super.updateById(receiptDemand);
    }

    @Override
    public List<ReceiptDemandAppVO> receiptDemandAppVOList(Integer warehouseId) {
        List<ReceiptDemandAppVO> receiptDemandList = receiptDemandMapper.getReceipetManageByApp(warehouseId);
        for (ReceiptDemandAppVO receiptDemandAppVO : receiptDemandList) {
            List<ReceiptCargoDetails> r = receiptListMapper.getReceiptCargoDetailsByReceiptManageId(receiptDemandAppVO.getReceiptManageId());
            receiptDemandAppVO.setReceiptListList(r);
        }
        return receiptDemandList;
    }

    @Override
    public List<ReceiptDemandAppVO> getReceipetManageByShelf(Integer warehouseId) {
        List<ReceiptDemandAppVO> receiptDemandList = receiptDemandMapper.getReceipetManageByShelf(warehouseId);
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Integer companyId = Integer.valueOf(request.getHeader("companyId"));
        for (ReceiptDemandAppVO receiptDemandAppVO : receiptDemandList) {

            List<ReceiptListVO> rvoList = new ArrayList<>();
            List<ReceiptList> rlist = receiptListMapper.getReceiptListByReceiptManageId(receiptDemandAppVO.getReceiptManageId());
            for (ReceiptList receiptList : rlist) {
                ReceiptListVO r = new ReceiptListVO();
                r.setId(receiptList.getId());
                r.setBoardNumber(receiptList.getBoardNumber());
//                r.setBatchNumber(receiptList.getBatchNumber());
//                r.setMaterialNumber(receiptList.getMaterialNumber());
//                r.setMaterialName(receiptList.getMaterialName());
                r.setWarehouseStorageNumber(receiptList.getWarehouseStorageNumber());
//                r.setReceiptManageId(receiptList.getReceiptManageId());
//                r.setUnit(receiptList.getUnit());
//                r.setUnitId(receiptList.getUnitId());
                r.setStatus(receiptList.getStatus());
//                UnitMappingDTO unitMappingDTO = projectService.getBillUnitByUnitId(receiptList.getUnitId(), companyId);
//                if (unitMappingDTO.getIsQuantityCount() == 1) {
//                    r.setCount(new BigDecimal(receiptList.getQuantityCount()));
//                } else if (unitMappingDTO.getIsBoxCount() == 1) {
//                    r.setCount(new BigDecimal(receiptList.getBoxCount()));
//                } else if (unitMappingDTO.getIsGrossWeight() == 1) {
//                    r.setCount(receiptList.getGrossWeight());
//                } else if (unitMappingDTO.getIsVolumeReceipt() == 1) {
//                    r.setCount(receiptList.getVolume());
//                }
//                rvoList.add(r);

            }
            receiptDemandAppVO.setReceiptLists(rvoList);
        }
        return receiptDemandList;
    }

    @Override
    public ReceiptList getReceiptListByBoardNumber(String boardNumber) {
        ReceiptList receiptList = receiptDemandMapper.getReceiptListByBoardNumber(boardNumber);
        if (receiptList == null) {
            throw new BusinessException("信息不存在，请确认");
        }
        return receiptList;
    }

}
