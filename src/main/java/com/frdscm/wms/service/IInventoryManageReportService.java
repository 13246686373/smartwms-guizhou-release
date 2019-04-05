package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.GetMaterialVO;
import com.frdscm.wms.entity.vo.InventoryManageVO;

import java.util.List;
import java.util.Map;

/**
 * 移位、调拨记录表 服务类
 *
 * @author March_CD
 * @since 2018-07-07
 */
public interface IInventoryManageReportService extends IService<InventoryManage> {

   /**
    * 查询移位或调拨列表
    * @param page
    * @param inventoryManagePageDTO
    * @return
    */
   Page<InventoryManage> getInventoryManageRecordByPageList(Page<InventoryManage> page, InventoryManagePageDTO inventoryManagePageDTO,String tableName);

}
