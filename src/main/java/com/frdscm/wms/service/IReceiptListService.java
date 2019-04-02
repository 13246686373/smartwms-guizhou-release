package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.dto.ReceiptItemListDTO;
import com.frdscm.wms.entity.dto.ReceiptListAppDTO;
import com.frdscm.wms.entity.dto.ReceiptListDTO;
import com.frdscm.wms.entity.dto.UpperShelfDTO;
import com.frdscm.wms.entity.vo.MaterialListAppVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 收货列表 服务类
 *
 * @author March_CD
 * @since 2018-07-06
 */
public interface IReceiptListService extends IService<ReceiptList> {

    /**
     * App收货操作
     *
     * @param receiptListDTO
     * @return
     */
    ReceiptList updateApp(ReceiptListAppDTO receiptListDTO);

    /**
     * 保存明细
     *
     * @param receiptListDTO
     */
    ReceiptListDTO save(ReceiptListDTO receiptListDTO);

    /**
     * @param receiptManageId
     */
    Page<ReceiptList> batchBoardNumber(Integer receiptManageId);

    /**
     * 修改收货明细信息
     *
     * @param receiptListDTO
     */
    void edit(ReceiptListDTO receiptListDTO);


    /**
     * 上架
     *
     * @param
     */
    void upperShelf(List<UpperShelfDTO> upperShelfDTOList);

    /**
     * 上架
     *
     * @param
     */
    void appUpperShelf(Integer id, String boardNumber, String warehouseStorageNumber);

    /**
     * 删除收货明细
     *
     * @param id 收货明细ID
     */
    void delete(Integer id);

    /**
     * 根据收货管理ID查询物料明细
     *
     * @param receiptManageId 收货管理ID
     * @return 物料明细List
     */
    List<ReceiptList> getReceiptListByReceiptManageId(@Param("receiptManageId") Integer receiptManageId);

    /**
     * 根据收货管理ID查询物料明细分页
     *
     * @param page
     * @param receiptManageId
     * @return
     */
    Page<ReceiptList> getReceiptListByReceiptManageIdPage(Page<ReceiptList> page, Integer receiptManageId);

    /**
     * 根据
     *
     * @param receiptManageId
     * @return
     */
    List<MaterialListAppVO> getMaterialListByReceiptManageId(Integer receiptManageId);

    /**
     * 根据收货需求ID删除信息
     *
     * @param receiptManageId
     */
    void deleteByReceiptManageId(Integer receiptManageId);

    void delete(List<Integer> ids);

    void deleteItem(Integer id);

}
