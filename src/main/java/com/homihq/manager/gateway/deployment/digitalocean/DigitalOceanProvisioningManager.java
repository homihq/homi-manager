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



    @EventListener
    @Async
    public void handle(CreateGatewayOrderEvent createGatewayOrderEvent) {

        log.info("Starting provisioning gateway");


        digitalOceanAppService.create(
                createGatewayOrderEvent.getGateway().getDoApiToken(),
                createGatewayOrderEvent.getGateway().getDoProjectId(),
                createGatewayOrderEvent.getGateway().getName(),
                createGatewayOrderEvent.getGateway().getId(),
                createGatewayOrderEvent.getGateway().getContainerCount(),
                createGatewayOrderEvent.getContainer().getSlug(),
                createGatewayOrderEvent.getGateway().getRegion().getSlug()

        );



    }


}
