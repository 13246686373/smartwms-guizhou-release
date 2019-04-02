package com.frdscm.wms.feign.hystrix;

import com.frdscm.wms.entity.bo.client.Client;
import com.frdscm.wms.feign.ClientService;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceImpl implements ClientService {

    @Override
    public Integer createAdd(Client client, Integer companyId) {
        return null;
    }
}
