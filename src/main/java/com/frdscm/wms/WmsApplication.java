package com.frdscm.wms;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring Boot 启动类
 *
 * @author chengdong
 */
@EnableApolloConfig
@SpringBootApplication
@EnableFeignClients
public class WmsApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(WmsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("Service startup completed");
    }
}
