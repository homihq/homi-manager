package com.homihq.manager.gateway.deployment.digitalocean;

import com.homihq.manager.gateway.Gateway;
import com.homihq.manager.gateway.GatewayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
class DigitalOceanProvisioningManager {

    private final DigitalOceanAppService digitalOceanAppService;
    private final DigitalOceanRedisService digitalOceanRedisService;

    private final GatewayRepository gatewayRepository;


    public void handle() {

        log.info("Starting provisioning scheduler");

        //1. find all gateways where redis status = submitted
        Page<Gateway> gatewayPage =
        gatewayRepository.findByRedisStatus(Gateway.Status.SUBMITTED, PageRequest.of(0, 10));

        if(!gatewayPage.isEmpty()) {
            //start creating these redis databases
            List<Gateway> gateways = gatewayPage.getContent();
            log.info("Gateways - {}", gateways);
        }

        //1. create redis database



        //2. create app service


        //3. setup cloud flare


        //3. Provision Completed event
    }

    public void attachRedisToApp() {

    }
}
