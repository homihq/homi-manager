package com.homihq.manager.gateway.deployment.digitalocean;

import com.homihq.manager.gateway.event.CreateGatewayOrderEvent;
import com.homihq.manager.product.ProductVariant;
import com.homihq.manager.product.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
class DigitalOceanProvisioningManager {

    private final DigitalOceanAppService digitalOceanAppService;
    private final DigitalOceanRedisService digitalOceanRedisService;


    @EventListener
    @Async
    public void handle(CreateGatewayOrderEvent createGatewayOrderEvent) {

        log.info("Starting provisioning gateway");


        //1. create redis database
        digitalOceanRedisService.create(
                createGatewayOrderEvent.getGateway().getDoApiToken(),
                createGatewayOrderEvent.getGateway().getDoProjectId(),
                createGatewayOrderEvent.getGateway().getName() + "-" + createGatewayOrderEvent.getGateway().getId(),
                createGatewayOrderEvent.getGateway().isDbStandBy() ? 2 : 1,
                createGatewayOrderEvent.getDb().getSlug(),
                createGatewayOrderEvent.getGateway().getRegion().getSlug()
        );


        //2. create app service


        //3. setup cloud flare


        //3. Provision Completed event
    }

    public void attachRedisToApp() {

    }
}
