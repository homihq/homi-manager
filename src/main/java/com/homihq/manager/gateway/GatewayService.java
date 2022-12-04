package com.homihq.manager.gateway;

import com.homihq.manager.cloud.*;
import com.homihq.manager.core.event.EventPublisher;
import com.homihq.manager.event.ProvisionGatewayEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GatewayService {

    private final GatewayRepository gatewayRepository;

    private final RegionRepository regionRepository;


    private final EventPublisher eventPublisher;

    @Transactional
    public Gateway save(CreateGatewayCommand createGatewayCommand) {
        Gateway gateway = new Gateway();
        gateway.setName(createGatewayCommand.name);
        gateway.setDescription(createGatewayCommand.description);
        gateway.setDeleted(false);


        //cloud provider
        //CloudProvider cloudProvider = this.cloudProviderRegionRepository.findById(createGatewayCommand.cloudProviderId).get();
        //gateway.setCloudProvider(cloudProvider);

        //cloud region
        Region region = this.regionRepository.findById(createGatewayCommand.cloudRegionId).get();
        gateway.setRegion(region);


        gateway.setStatus(Gateway.GatewayStatus.CREATED);


        this.gatewayRepository.save(gateway);

        eventPublisher.publish(ProvisionGatewayEvent.builder().gateway(gateway).build());

        return gateway;
    }

    @Data
    public static class CreateGatewayCommand{

        private String name;
        private String description;
        private Long cloudProviderId;
        private Long cloudRegionId;
        private Long cloudGatewayPlanId;

    }
}
