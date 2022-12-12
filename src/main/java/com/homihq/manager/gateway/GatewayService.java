package com.homihq.manager.gateway;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GatewayService {

    private final GatewayRepository gatewayRepository;

    @Transactional
    public void updateRoutes(String gatewayKey, MultipartFile file) {
        Optional<Gateway> gateway =
        this.gatewayRepository.findByGatewayKey(gatewayKey);

        try {
            Yaml yaml = new Yaml();
            Map<String, List<RouteDefinition>> routes =
                    yaml.load(file.getInputStream());

            log.info("routes - {}", routes);
        }
        catch(Exception e) {
            log.info("Error", e);
        }

        log.info("Gateway located - {}", gateway.get());
    }

    @Transactional
    public void processMetaDataRequest(String gatewayKey,
                             Long version,
                             String instanceId) {
        Optional<Gateway> gateway = this.gatewayRepository.findByGatewayKey(gatewayKey);
        if(gateway.isPresent()) {
            Gateway g = gateway.get();
            //check for instance
            if(Objects.isNull(g.getInstances())) {
                GatwayInstance gi = new GatwayInstance();
                gi.setId(instanceId);
                gi.setCreatedDate(LocalDateTime.now());
                gi.setLastUpdatedDate(LocalDateTime.now());
                g.setInstances(List.of(gi));
            }
            else{
                Optional<GatwayInstance> gi = g.getInstances().stream()
                        .filter(i -> i.getId().equals(instanceId)).findFirst();

                if(gi.isEmpty()) { // new instance
                    GatwayInstance i = new GatwayInstance();
                    i.setId(instanceId);
                    i.setCreatedDate(LocalDateTime.now());
                    i.setLastUpdatedDate(LocalDateTime.now());
                    List<GatwayInstance> existingInstances = g.getInstances();
                    existingInstances.add(i);
                    g.setInstances(existingInstances);
                }
                else{
                    GatwayInstance gatwayInstance = gi.get();
                    gatwayInstance.setLastUpdatedDate(LocalDateTime.now());
                }
            }
            this.gatewayRepository.save(g);
        }

        //return DTO - version id, routes

    }

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
