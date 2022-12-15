package com.homihq.manager.gateway;

import com.homihq.manager.gateway.apidef.ApiDefinition;
import com.homihq.manager.gateway.apidef.DynamicRouteDefinitions;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        if(gateway.isPresent()) {
            try {

                Yaml yaml = new Yaml();
                ApiDefinition apiDefinition =
                        yaml.loadAs(file.getInputStream(), ApiDefinition.class);

                apiDefinition.validate();

                List<ApiDefinition> apiDefinitions = gateway.get().getApiDefinitions();
                boolean routesUpdated = false;


                if(Objects.isNull(apiDefinitions)) {
                    apiDefinition.setCreatedDate(LocalDateTime.now());
                    apiDefinition.setLastUpdatedDate(LocalDateTime.now());
                    apiDefinitions = List.of(apiDefinition);
                    routesUpdated = true;

                }
                else{ //api defs are present

                    //1. check if this api is present
                    Optional<ApiDefinition> optionalApiDefinition =
                    apiDefinitions.stream().filter(
                            def -> def.getId().equals(apiDefinition.getId())
                    ).findFirst();

                    if(optionalApiDefinition.isPresent()) {
                        //2. check for version change
                        ApiDefinition apiDef = optionalApiDefinition.get();
                        if(apiDef.getVersion().equals(apiDefinition.getVersion())) {
                            log.info("API Definition exits with same version ignoring.");

                        }
                        else{
                            log.info("API Definition exits with different version spotted.Preparing update");
                            apiDefinition.setCreatedDate(apiDef.getCreatedDate());
                            apiDefinition.setLastUpdatedDate(LocalDateTime.now());
                            apiDefinitions.remove(apiDef);
                            apiDefinitions.add(apiDefinition);
                            routesUpdated = true;

                        }
                    }
                    else{
                        log.info("API Definition not found. New definition will be added.");
                        apiDefinition.setCreatedDate(LocalDateTime.now());
                        apiDefinition.setLastUpdatedDate(LocalDateTime.now());
                        apiDefinitions.add(apiDefinition);
                        routesUpdated = true;


                    }

                }


                if(routesUpdated) {
                    log.info("Routes updated");
                    Gateway gw = gateway.get();

                    gw.incrementRouteVersion();
                    gw.setApiDefinitions(apiDefinitions);

                    this.gatewayRepository.save(gw);
                }
            }
            catch(Exception e) {
                log.info("Error", e);
            }
        }
        else{
            log.info("Gateway not found.");
        }


        log.info("Gateway located - {}", gateway.get());
    }

    @Transactional
    public DynamicRouteDefinitions queryApiDefinitions(String gatewayKey,
                                                   String instanceId) {
        Optional<Gateway> gateway = this.gatewayRepository.findByGatewayKey(gatewayKey);

        if(gateway.isPresent()) {
            log.info("Gateway found.");
            List<ApiDefinition> definitions = gateway.get().getApiDefinitions();

            log.info("definitions = {}", definitions);
            this.updateInstance(gatewayKey, instanceId);
            return DynamicRouteDefinitions.builder().apiDefinitions(definitions)
                    .version(gateway.get().getRouteVersion()).build();
        }
        else{
            log.info("Gateway not found.");
            return DynamicRouteDefinitions.builder().build();
        }
    }
    @Transactional
    public void updateInstance(String gatewayKey,
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
