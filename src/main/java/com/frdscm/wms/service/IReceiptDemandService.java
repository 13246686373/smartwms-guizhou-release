package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ReceiptDemand;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.ReceiptManage;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.InventoryManagePageDTO;
import com.frdscm.wms.entity.dto.ReceiptDemandDTO;
import com.frdscm.wms.entity.dto.ReceiptDemandPageDTO;
import com.frdscm.wms.entity.vo.ReceiptDemandAppVO;
import com.frdscm.wms.entity.vo.ReceiptDemandVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 收货需求表 服务类
 *
 * @author dizhang
 * @since 2018-07-03
 */
public interface IReceiptDemandService extends IService<ReceiptDemand> {

    /**
     * 保存收货需求
     *
     * @param receiptDemandDTO 收货需求
     * @param userBO           用户
     */
    ReceiptDemandDTO save(ReceiptDemandDTO receiptDemandDTO, UserBO userBO);

    /**
     * 修改收货需求信息
     *
     * @param receiptDemandDTO 收货需求
     */
    ReceiptDemandDTO edit(ReceiptDemandDTO receiptDemandDTO);

    /**
     * 删除收货需求
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 查询收货需求表
     *
     * @param page
     * @param receiptDemandPageDTO
     * @return
     */
    Page<ReceiptDemandVO> getReceiptCargoDetailsList(Page<ReceiptDemandVO> page, ReceiptDemandPageDTO receiptDemandPageDTO);

    /**
     * 修改收货状态
     *
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Integer id);

    /**
     * 确认收货需求
     */
    void confirmDemand(ReceiptDemand receiptDemand, Integer companyId);

    /**
     * 确认收货需求
     */
    void cancelDemand(ReceiptDemand receiptDemand);

    /**
     * 查询收货需求表
     *
     * @return
     */
    List<ReceiptDemandAppVO> receiptDemandAppVOList(Integer warehouseId);

    /**
     * 查询待上架的列表
     *
     * @return
     */
    List<ReceiptDemandAppVO> getReceipetManageByShelf(Integer warehouseId);

    /**
     * 根据板号差选收货需求表
     *
     * @param boardNumber
     * @return
     */
    ReceiptList getReceiptListByBoardNumber(String boardNumber);


}
