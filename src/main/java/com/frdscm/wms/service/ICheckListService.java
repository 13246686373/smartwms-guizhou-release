package com.frdscm.wms.service;

import com.frdscm.wms.entity.CheckList;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.dto.CheckListAppDTO;
import com.frdscm.wms.entity.dto.CheckListDTO;
import com.frdscm.wms.entity.dto.CheckListUpdateDTO;
import com.frdscm.wms.entity.dto.GetCheckListDTO;
import com.frdscm.wms.entity.vo.CheckListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
public interface ICheckListService extends IService<CheckList> {
    /**
     * 根据盘点信息ID查询盘点详情
     * @param checkManageId
     * @return
     */
    List<CheckList> getCheckListByCheckManageId(Integer checkManageId);


    /**
     * 获取储位
     * @param checkManageId
     * @return
     */
    String getCheckListByCheckManageIdApp(Integer checkManageId);

    /**
     * 移除盘点库位
     * @param warehouseStorageNum
     * @return
     */
    void checkListRemove(String warehouseStorageNum,Integer checkManageId);

    /**
     * 修改盘点数量
     * @param checkListDTO
     */
    void update(CheckListDTO checkListDTO);

    /**
     * APP盘点数量
     * @param checkListAppDTO
     */
    void checkUpdate(CheckListAppDTO checkListAppDTO);

    /**
     * 根据板号和储位号查询盘点信息
     * @return
     */
    CheckListVO getCheckList(GetCheckListDTO getCheckListDTO);

}
