package com.frdscm.wms.service;

import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.dto.ReceiptCargoDetailsDTO;

import java.util.List;

/**
 * 收货需求货物明细表 服务类
 *
 * @author March_CD
 * @since 2018-07-03
 */
public interface IReceiptCargoDetailsService extends IService<ReceiptCargoDetails> {

    /**
     * 保存收货需求明细
     * @param receiptCargoDetailsDTO
     */
    void save(ReceiptCargoDetailsDTO receiptCargoDetailsDTO);

    /**
     * 修改收货信息明细
     * @param receiptCargoDetailsDTO
     */
    void edit(ReceiptCargoDetailsDTO receiptCargoDetailsDTO);

    /**
     * 删除收货明细信息
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据收货需求ID 查询物料信息
     * @param receiptDemandId
     * @return
     */
    List<ReceiptCargoDetails> getReceiptCargoDetailsByReceiptDemandId(Integer receiptDemandId);
}
