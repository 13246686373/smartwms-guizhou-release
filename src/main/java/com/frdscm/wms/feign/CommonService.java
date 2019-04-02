package com.frdscm.wms.feign;

import com.frdscm.wms.entity.bo.client.Client;
import com.frdscm.wms.entity.bo.client.Dictionary;
import com.frdscm.wms.feign.hystrix.ClientServiceImpl;
import com.frdscm.wms.feign.hystrix.CommonServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/10/29 1:40 PM
 */
@FeignClient(value = "common-service", fallback = CommonServiceImpl.class)
public interface CommonService {

    @RequestMapping(value = "/common/dictionary/{type}",method = RequestMethod.GET)
    List<Dictionary> getDictionary(@PathVariable("type") String type, @RequestHeader("companyId") Integer companyId);
}
