package com.frdscm.wms.feign;

import com.frdscm.wms.feign.hystrix.AccountServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/10/29 1:40 PM
 */
@FeignClient(value = "account-service", fallback = AccountServiceImpl.class)
public interface AccountService {

}
