package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.CheckManage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.dto.CheckManagePageDTO;
import com.frdscm.wms.entity.vo.CheckManageVO;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
@Repository
public interface CheckManageMapper extends BaseMapper<CheckManage> {

    /**
     * 查询LIST
     *
     * @param page
     * @param checkManagePageDTO
     * @return
     */
    List<CheckManageVO> selectCheckPage(Pagination page, CheckManagePageDTO checkManagePageDTO);

    @Update("update check_manage set status= 2 where id = #{id}")
    void confirm(@Param("id") Integer id);


    /**
     * 盘点单列表
     *
     * @return
     */
    @Select("Select * from check_manage where warehouse_id = #{warehouseId} and status = 1")
    List<CheckManage> checkManageList(@Param("warehouseId") Integer warehouseId);

    @Select("select ifnull(max(right(check_number, 3)),0) + 1 from check_manage where check_number like concat(#{checkNumber},'%')")
    int countCheckNumber(@Param("checkNumber") String checkNumber);
}
