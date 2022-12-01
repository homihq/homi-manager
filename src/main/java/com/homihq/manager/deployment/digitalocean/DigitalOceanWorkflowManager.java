package com.homihq.manager.deployment.digitalocean;

import com.homihq.manager.cloud.digitalocean.DigitalOceanRedis;
import com.homihq.manager.deployment.digitalocean.DigitalOceanAppService;
import com.homihq.manager.deployment.digitalocean.DigitalOceanRedisService;
import com.homihq.manager.event.ProvisionGatewayEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class DigitalOceanWorkflowManager {

    private final DigitalOceanAppService digitalOceanAppService;
    private final DigitalOceanRedisService digitalOceanRedisService;


    @Value("${digitalocean.token}")
    private String apiKey;

    @EventListener
    @Async
    public void handle(ProvisionGatewayEvent provisionGatewayEvent) {
        //1. create redis database

        DigitalOceanRedis digitalOceanRedis =
        digitalOceanRedisService.create(
                apiKey, "test-redis-4-gw-essentials", "",
                1,
                "db-s-1vcpu-1gb" , "nyc1"
        );

        //2. create app service


        //3. setup cloud flare


        //3. Provision Completed event
    }

    public void attachRedisToApp() {

    }
}
