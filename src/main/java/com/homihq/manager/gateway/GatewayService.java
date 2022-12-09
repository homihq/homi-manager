package com.homihq.manager.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
        gateway.setGatewayKey("homi_"+ UUID.randomUUID().toString());

        return this.gatewayRepository.save(gateway);

    }


    @Transactional(readOnly = true)
    public Page<Gateway> searchByName(String name, Pageable page) {


        if(StringUtils.isEmpty(name)) {
            return this.gatewayRepository.findByDeleted(false, page);
        }
        else{
            return this.gatewayRepository.findByNameLikeIgnoreCaseAndDeleted("%" + name + "%", false, page);
        }

    }


    @Data
    public static class CreateGatewayCommand{

        private String name;
        private String description;


    }
}
