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
 * 库存管理(报表)表 服务类
 *
 * @author March_CD
 * @since 2018-07-07
 */
public interface IInventoryManageReportService extends IService<InventoryManage> {

 /**
  * 增加移位、调拨操作前记录
  * @param receiptList
  * @param receiptDemandPickUpDTO
  * @param userBO
  */
  void  addInventoryReportManage(InventoryManage inventoryReportManage);

}
