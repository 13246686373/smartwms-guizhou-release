package com.frdscm.wms.feign;

import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.vo.OrderVo;
import com.frdscm.wms.feign.hystrix.OrderServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "order-service", fallback = OrderServiceImpl.class)
public interface OrderService {

    /**
     * 新增订单
     * @param orderVo
     * @param id
     * @param username
     * @return
     */
    @RequestMapping(value = "order/order/save", method = RequestMethod.POST)
    Response save(OrderVo orderVo, @RequestHeader("id") Integer id, @RequestHeader("username") String username, @RequestHeader("companyId") Integer companyId);
}
