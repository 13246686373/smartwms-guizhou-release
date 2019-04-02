package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ReceiptManage;
import com.frdscm.wms.entity.dto.CountDTO;
import com.frdscm.wms.entity.dto.ReceiptManagePageDTO;
import com.frdscm.wms.entity.vo.ReceiptCountVO;

import java.util.List;
import java.util.Map;

/**
 * 收货管理表 服务类
 *
 * @author March_CD
 * @since 2018-07-06
 */
public interface IReceiptManageService extends IService<ReceiptManage> {

    /**
     * 保存收货关系信息
     *
     * @param receiptManage
     */
    void save(ReceiptManage receiptManage);

    /**
     * 修改收貨管理
     *
     * @param receiptManage
     */
    void edit(ReceiptManage receiptManage);

    /**
     * 根据收货需求ID查询收货管理信息
     *
     * @param receiptDemandId 收货需求ID
     * @return
     */
    ReceiptManage selectReceiptManageByReceiptDemandId(Integer receiptDemandId);

    /***
     *  根据ID查询收货管理信息
     * @param receiptManageId
     * @return
     */
    ReceiptManage selectReceiptManageByReceiptManageId(Integer receiptManageId);

    /**
     * 查询收货列表
     *
     * @param receiptManagePageDTO
     * @param page
     * @return
     */
    Page<Map<String, Object>> getReceiptManageByPageList(Page<Map<String, Object>> page, ReceiptManagePageDTO receiptManagePageDTO);

    /**
     * 根据收货需求ID删除收货管理信息
     *
     * @param receiptDemandId 收货需求ID
     */
    void deleteReceiptManageByReceiptDemandId(Integer receiptDemandId);

    /**
     * 修改收货状态
     *
     * @param receiptManageId
     * @param status
     */
    void updateStatus(Integer receiptManageId, Integer status);


    /**
     * 看板统计
     *
     * @param receiptCountDTO
     * @return
     */
    Map getReceiptBoardCount(CountDTO receiptCountDTO);

    /**
     * 看板数据统计
     *
     * @param receiptCountDTO
     * @return
     */
    Page<ReceiptCountVO> getReceiptDataCount(CountDTO receiptCountDTO);


    List<Map<String, Object>> getReceiptDateCount(CountDTO receiptCountDTO);

    void completeReceipt(Integer id, Integer companyId);
}
