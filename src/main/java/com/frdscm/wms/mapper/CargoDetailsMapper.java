package com.frdscm.wms.mapper;

import com.frdscm.wms.entity.CargoDetails;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.dto.CargoDetailsDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dizhang
 * @since 2019-04-05
 */
@Repository
public interface CargoDetailsMapper extends BaseMapper<CargoDetails> {

    @Update("update cargo_details set enabled_flag = 'N' where order_id = ${orderId}")
    void updateExpenseItem(@Param("orderId") Integer orderId);

    @Select("select * from cargo_details where enabled_flag = 'Y' and order_id = ${orderId}")
    List<CargoDetailsDTO> getCargoDetailsByOrderId(@Param("orderId") Integer orderId);
}
