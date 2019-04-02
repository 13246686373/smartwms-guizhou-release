package com.frdscm.wms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.entity.CheckList;
import com.frdscm.wms.entity.CheckManage;
import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.dto.CheckManageDTO;
import com.frdscm.wms.entity.dto.CheckManagePageDTO;
import com.frdscm.wms.entity.vo.CheckManageVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
public interface ICheckManageService extends IService<CheckManage> {

    /**
     * 添加盘点
     * @param checkManageDTO
     * @return
     */
    void add(CheckManageDTO checkManageDTO);

    /**
     * 查询LIST
     * @param page
     * @param checkManagePageDTO
     * @return
     */
    Page<CheckManageVO> selectCheckPage(Page<CheckManageVO> page, CheckManagePageDTO checkManagePageDTO);

    /**
     * 删除盘点信息
     * @param checkManageId
     */
    void delete(Integer checkManageId);

    /**
     * 完成盘点任务
     * @param checkManageId
     */
    void confirm(Integer checkManageId);

    /**
     * 盘点单列表
     * @return
     */
    List<CheckManage> checkManageList(Integer warehouseId);
}
