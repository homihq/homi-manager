package com.homihq.manager.gateway.deployment.digitalocean;


import com.homihq.manager.gateway.deployment.DeploymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class DigitalOceanAppService {

    public static final String DOCKER_REGISTRY = "homihq";
    public static final String REGISTRY_TYPE = "DOCKER_HUB";
    public static final String DOCKER_REPOSITORY = "micro-gateway";
    public static final String IMAGE_TAG = "latest";
    public static final String SERVICE_NAME = "homi-gateway";
    private final String digitalOceanUrl = "https://api.digitalocean.com/v2/apps";

    private final RestTemplate restTemplate;


    public DigitalOceanApp create(String doToken, String doProjectId,String gatewayName,
                                  Long gatewayId,
                                  int noOfNodes, String sizeSlug,
                                    String regionSlug) {

        String appName = gatewayName + "-" + UUID.randomUUID().toString().substring(1,8);
        log.info("appName - {}", appName);
        DigitalOceanApp.Image image = getImage();

        DigitalOceanApp.Service service = getService(noOfNodes, sizeSlug, image);


        DigitalOceanApp.AppSpec appSpec = DigitalOceanApp.AppSpec.builder().
                name(appName).services(List.of(service)).
                region(regionSlug).build();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + doToken);

        DigitalOceanApp createGatewayRequest = new DigitalOceanApp(appSpec, null, doProjectId);
        HttpEntity<DigitalOceanApp> entity = new HttpEntity<>(createGatewayRequest, headers);


        try {
            ResponseEntity<String> result = restTemplate
                    .postForEntity(digitalOceanUrl, entity, String.class);

            log.info("Body - {}",  result.getBody());
            return createGatewayRequest;
        }
        catch(Exception e) {
            log.error("Error creating app service - {}", e);
            throw new DeploymentException("Failed to create Gateway app service", e);
        }

    }

    private DigitalOceanApp.Service getService(int noOfNodes, String sizeSlug, DigitalOceanApp.Image image) {
        DigitalOceanApp.Service service =DigitalOceanApp.Service.builder().
                image(image).
                instanceCount(noOfNodes).
                name(SERVICE_NAME).
                instanceSizeSlug(sizeSlug).
                routes(
                        List.of(
                                DigitalOceanApp.Route.builder().path("/").build()
                        )).build();
        return service;
    }

    private DigitalOceanApp.Image getImage() {
        DigitalOceanApp.Image image = DigitalOceanApp.Image.builder()
                .registry(DOCKER_REGISTRY).
                registryType(REGISTRY_TYPE).
                repository(DOCKER_REPOSITORY).
                tag(IMAGE_TAG).build();
        return image;
    }

    public void delete() {

    }
}
