package com.homihq.manager.gateway;

import com.homihq.manager.gateway.event.CreateGatewayOrderEvent;
import com.homihq.manager.product.*;
import com.homihq.manager.core.event.EventPublisher;
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
    private final ProductVariantRepository productVariantRepository;

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

        gateway.setDoApiToken(createGatewayCommand.doTokenId);
        gateway.setDoProjectId(createGatewayCommand.doProjectId);

        gateway.setRedisStatus(Gateway.Status.SUBMITTED);
        gateway.setAppStatus(Gateway.Status.SUBMITTED);
        gateway.setStatus(Gateway.Status.SUBMITTED);

        gateway.setContainerId(createGatewayCommand.containerId);
        gateway.setContainerCount(createGatewayCommand.noOfInstances);
        gateway.setDbId(createGatewayCommand.dbId);
        gateway.setDbStandBy(createGatewayCommand.standbyInstance);

        this.gatewayRepository.save(gateway);

        ProductVariant db = this.productVariantRepository.getReferenceById(createGatewayCommand.dbId);

        this.eventPublisher.publish(CreateGatewayOrderEvent.builder().gateway(gateway).db(db)
                .build());

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
