package com.homihq.manager.gateway;

import com.homihq.manager.product.*;
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


        //region
        Region region = this.regionRepository.findById(createGatewayCommand.regionId).get();
        gateway.setRegion(region);




        gateway.setStatus(Gateway.Status.CREATED);


        this.gatewayRepository.save(gateway);

        eventPublisher.publish(ProvisionGatewayEvent.builder().gateway(gateway).build());

        return gateway;
    }

    @Data
    public static class CreateGatewayCommand{

        private String name;
        private String description;

        private Integer regionId;

        private Integer dbId;

        private boolean standbyInstance;

        private Integer containerId;

        private int noOfInstances = 1;

        private String doProjectId;

        private String doTokenId;

    }
}
