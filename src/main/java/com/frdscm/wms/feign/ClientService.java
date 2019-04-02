package com.frdscm.wms.feign;

import com.frdscm.wms.entity.bo.client.Client;
import com.frdscm.wms.feign.hystrix.ClientServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/10/29 1:40 PM
 */
@FeignClient(value = "client-service", fallback = ClientServiceImpl.class)
public interface ClientService {

    @RequestMapping(value = "client/add", method = RequestMethod.POST)
    Integer createAdd(@RequestBody Client client, @RequestHeader("companyId") Integer companyId);
}
