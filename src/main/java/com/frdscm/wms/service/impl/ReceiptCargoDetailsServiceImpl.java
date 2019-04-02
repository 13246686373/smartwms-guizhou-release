package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.dto.ReceiptCargoDetailsDTO;
import com.frdscm.wms.mapper.ReceiptCargoDetailsMapper;
import com.frdscm.wms.service.IReceiptCargoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收货需求货物明细表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Service
public class ReceiptCargoDetailsServiceImpl extends ServiceImpl<ReceiptCargoDetailsMapper, ReceiptCargoDetails> implements IReceiptCargoDetailsService {

    @Autowired
    ReceiptCargoDetailsMapper receiptCargoDetailsMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(ReceiptCargoDetailsDTO receiptCargoDetailsDTO) {
        ReceiptCargoDetails receiptCargoDetails = BeanUtils.copy(receiptCargoDetailsDTO,ReceiptCargoDetails.class);
        super.insert(receiptCargoDetails);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void edit(ReceiptCargoDetailsDTO receiptCargoDetailsDTO) {
        ReceiptCargoDetails receiptCargoDetails = BeanUtils.copy(receiptCargoDetailsDTO,ReceiptCargoDetails.class);
        super.updateById(receiptCargoDetails);
    }

    @Override
    public void delete(Integer id) {
        ReceiptCargoDetails receiptCargoDetails = super.selectById(id);
        if(null == receiptCargoDetails){
            throw new BusinessException("收货需求明细不存在，请确认");
        }
        super.deleteById(id);
    }

    @Override
    public List<ReceiptCargoDetails> getReceiptCargoDetailsByReceiptDemandId(Integer receiptDemandId) {
        return receiptCargoDetailsMapper.getReceiptCargoDetailsByReceiptDemandId(receiptDemandId);
    }


}
