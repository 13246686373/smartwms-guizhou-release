package com.frdscm.wms.feign.hystrix;

import com.frdscm.wms.entity.bo.client.Dictionary;
import com.frdscm.wms.feign.CommonService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonServiceImpl implements CommonService {

    @Override
    public List<Dictionary> getDictionary(String type, Integer companyId) {
        return null;
    }
}
