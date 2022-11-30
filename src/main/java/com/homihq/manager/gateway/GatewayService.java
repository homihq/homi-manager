package com.homihq.manager.gateway;

import com.homihq.manager.cloud.*;
import com.homihq.manager.core.event.EventPublisher;
import com.homihq.manager.event.ProvisionGatewayEvent;
import com.homihq.manager.project.domain.Project;
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

    private final CloudProviderRepository cloudProviderRepository;
    private final CloudRegionRepository cloudRegionRepository;
    private final CloudGatewayPlanRepository cloudGatewayPlanRepository;

    private final EventPublisher eventPublisher;

    @Transactional
    public Gateway save(CreateGatewayCommand createGatewayCommand) {
        Gateway gateway = new Gateway();
        gateway.setName(createGatewayCommand.name);
        gateway.setDescription(createGatewayCommand.description);
        gateway.setDeleted(false);
        gateway.setProject(Project.builder().id(createGatewayCommand.projectId).build());

        //cloud provider
        CloudProvider cloudProvider = this.cloudProviderRepository.findById(createGatewayCommand.cloudProviderId).get();
        gateway.setCloudProvider(cloudProvider);

        //cloud region
        CloudRegion cloudRegion = this.cloudRegionRepository.findById(createGatewayCommand.cloudRegionId).get();
        gateway.setCloudRegion(cloudRegion);

        CloudGatewayPlan cloudGatewayPlan = this.cloudGatewayPlanRepository.findById(createGatewayCommand.cloudGatewayPlanId).get();
        gateway.setCloudGatewayPlan(cloudGatewayPlan);
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
        private Long projectId;
        private Long cloudGatewayPlanId;

    }
}
