package com.homihq.manager.gateway;

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

    @Transactional
    public Gateway save(CreateGatewayCommand createGatewayCommand) {
        Gateway gateway = new Gateway();
        gateway.setName(createGatewayCommand.name);
        gateway.setDescription(createGatewayCommand.description);
        gateway.setDeleted(false);

        gateway.setStatus(Gateway.Status.CREATED);

        return this.gatewayRepository.save(gateway);

    }

    @Data
    public static class CreateGatewayCommand{

        private String name;
        private String description;
        

    }
}
