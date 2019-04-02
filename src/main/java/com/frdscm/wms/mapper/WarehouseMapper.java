package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.Warehouse;
import com.frdscm.wms.entity.dto.WarehousePageDTO;
import com.frdscm.wms.entity.vo.WarehouseVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 仓库表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Repository
public interface WarehouseMapper extends BaseMapper<Warehouse> {

    /**
    * @author: chengdong
    * @description: 统计数量
    */
    @Select("SELECT COUNT(id) FROM warehouse")
    int selectCount();

    /**
     * 获取仓库列表
     * @return
     */
    @Select("select * from warehouse where status = 1")
    List<Warehouse> getWarehouseList();

    /**
     * 查询仓库
     * @param warehousePageDTO
     * @param page
     * @return
     */
    List<WarehouseVO> selectByPageList(Pagination page,WarehousePageDTO warehousePageDTO);
}
