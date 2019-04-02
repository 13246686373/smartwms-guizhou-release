package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.entity.InventoryManage;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.GetMaterialVO;
import com.frdscm.wms.entity.vo.InventoryListVO;
import com.frdscm.wms.entity.vo.InventoryManageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 库存管理表 服务类
 *
 * @author March_CD
 * @since 2018-07-07
 */
public interface IInventoryManageService extends IService<InventoryManage> {

    /**
     * 查询库存列表
     * @param page
     * @param inventoryManagePageDTO
     * @return
     */
    Page<InventoryManage> getInventoryManageByPageList(Page<InventoryManage> page, InventoryManagePageDTO inventoryManagePageDTO);


    /**
     * 修改库存状态
     * @param status 状态 0 - 不良品 1 - 良品
     * @param id 库存ID
     */
   void updateStatus(Integer status,Integer id);

    /**
     * 拆板
     * @param inventoryManageDTOList
     * @return
     */
   InventoryManage dismantlingPlate(DismantlingPlateDTO dismantlingPlateDTO);


    /**
     * 货物上架
     * @param receiptList  货物明细
     * @param receiptDemandPickUpDTO 储位信息
     * @param userBO
     */
   String  addInventoryManage(ReceiptList receiptList,ReceiptDemandPickUpDTO receiptDemandPickUpDTO, UserBO userBO);

    /**
     * 仓库移动储位
     * @param moveWereHouseStorageDTO
     */
    void moveWarehouseStorage(MoveWereHouseStorageDTO moveWereHouseStorageDTO);

    /**
     * 仓库移动储位
     * @param moveWereHouseStorageDTO
     */
    void moveWarehouseStorageApp(MoveWereHouseStorageAppDTO moveWereHouseStorageAppDTO);

    /**
     * 仓库移动储位
     * @param moveWereHouseStorageDTO
     */
    void transferWereHouseStorage(TransferWereHouseStorageDTO transferWereHouseStorageDTO);


    /**
     * 统计板号
     * @param boardNumberLike
     * @return
     */
    int countBoardNumber(String boardNumberLike);


    /**
     * 查询批次号
     * @param selectbatchNumberDTO
     * @return
     */
    List<String> getBatchNumberList(SelectbatchNumberDTO selectbatchNumberDTO);

    /**
     * 查询库存
     * @param checkManageSelectDTO
     * @return
     */
    List<InventoryManage> getInventoryManageList(CheckManageSelectDTO checkManageSelectDTO);

    /**
     * 出货详情信息
     * @param page
     * @param inventoryManageByShipmentPageDTO
     * @return
     */
    Page<InventoryManage> getInventoryManageListByShipment(Page<InventoryManage> page,InventoryManageByShipmentPageDTO inventoryManageByShipmentPageDTO);

    /**
     * 查询物料信息
     * @param materialDTO
     * @return
     */
    List<GetMaterialVO> getMaterialList(MaterialDTO materialDTO);

    /**
     * 物料查询
     * @param freightInquiryDTO
     * @return
     */
    List<InventoryManageVO> freightInquiry(FreightInquiryDTO freightInquiryDTO);

    /**
     * 移动端拆板返回板号
     * @param dismantlingPlateAppDTO
     * @return
     */
    Map<String, String> dismantlingPlateApp(DismantlingPlateAppDTO dismantlingPlateAppDTO);

    /**
     * 合版
     * @param mergePlateDTO
     * @return
     */
    String mergePlate(MergePlateDTO mergePlateDTO);

    /**
     * 合版
     * @param mergePlateDTO
     * @return
     */
    Map<String, String> mergePlateApp(MergePlateAppDTO mergePlateAppDTO);

    /**
     * 拆板前查询物料信息
     * @param dismantlingPlateAppDTO
     * @return
     */
    InventoryManage getInventoryManageByWarehouseIdAndBoardNumber(DismantlingPlateAppDTO dismantlingPlateAppDTO);
}
