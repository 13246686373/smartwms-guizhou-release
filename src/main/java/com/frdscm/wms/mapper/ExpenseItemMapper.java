package com.frdscm.wms.mapper;

import com.frdscm.wms.entity.ExpenseItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.dto.ExpenseItemDTO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
public interface ExpenseItemMapper extends BaseMapper<ExpenseItem> {

    @Update("update expense_item set enabled_flag = 'N' where order_id = ${orderId}")
    void updateExpenseItem(@Param("orderId") Integer orderId);


    @Select("select * from expense_item where enabled_flag = 'Y' and order_id = ${orderId}")
    List<ExpenseItemDTO> getExpenseItemByOrderId(@Param("orderId") Integer orderId);

    @Select("select sum(expense_item_total) from expense_item where order_no = '${orderNo}'")
    BigDecimal getOrderExpenseItemTotal(@Param("orderNo") String orderNo);

}
