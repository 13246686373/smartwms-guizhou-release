package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.Warehouse;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.WarehouseDTO;
import com.frdscm.wms.entity.dto.WarehousePageDTO;
import com.frdscm.wms.entity.vo.WarehouseVO;

import java.util.List;

/**
 * 仓库表 服务类
 *
 * @author March_CD
 * @since 2018-07-03
 */
public interface IWarehouseService extends IService<Warehouse> {
    /**
     * @author: chengdong
     * @param: warehouseStorageDTO 仓库对象
     * @param: userBO 用户信息
     * @description: 保存仓库信息
     */
    Warehouse save(WarehouseDTO warehouseDTO, UserBO userBO);

    /**
     * @author: chengdong
     * @param: warehouseStorageDTO 仓库对象
     * @description: 修改仓库信息
     */
    void edit(WarehouseDTO warehouseDTO);

    /**
     * @author: chengdong
     * @param: id 仓库ID
     * @description: 删除仓库信息
     */
    void delete(Integer id);

    /**
     * 查询仓库列表
     *
     * @return
     */
    List<Warehouse> getWarehouseList();

    /**
     * 查询仓库信息
     *
     * @param page
     * @param warehousePageDTO
     * @return
     */
    Page<WarehouseVO> selectByPage(Page<WarehouseVO> page, WarehousePageDTO warehousePageDTO);
}
