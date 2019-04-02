package com.frdscm.wms.feign.hystrix;

import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.vo.OrderVo;
import com.frdscm.wms.feign.OrderService;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    @Override
    public Response save(OrderVo orderVo, Integer id, String username, Integer companyId) {
        return null;
    }
}
