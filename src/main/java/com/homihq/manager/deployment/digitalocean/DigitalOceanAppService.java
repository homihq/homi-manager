package com.homihq.manager.deployment.digitalocean;


import com.homihq.manager.cloud.digitalocean.DigitalOceanApp;
import com.homihq.manager.deployment.DeploymentException;
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

    private final String digitalOceanUrl = "https://api.digitalocean.com/v2/apps";

    private final RestTemplate restTemplate;

    public DigitalOceanApp create(String digitalOceanToken, String digitalOceanProjectId,
                       String cnameRecord, String gatewayName, String region) {
        List<DigitalOceanApp.Domain> domains = List.of(DigitalOceanApp.Domain
                .builder().domain(cnameRecord).type("PRIMARY")
                .build());

        DigitalOceanApp.Image image = DigitalOceanApp.Image.builder()
                .registry("homihq").
                registryType("DOCKER_HUB").
                repository("micro-gateway").
                tag("latest").build();

        String name = gatewayName + "-" +UUID.randomUUID().toString().substring(1,8);



        DigitalOceanApp.Service service =DigitalOceanApp.Service.builder().
                image(image).
                httpPort(8080).
                instanceCount(1).
                name("homihq-gateway").
                instanceSizeSlug("basic-xs").
                routes(
                        List.of(
                                DigitalOceanApp.Route.builder().path("/").build()
                        )).build();


        DigitalOceanApp.AppSpec appSpec = DigitalOceanApp.AppSpec.builder().
                domains(domains).
                name(name).services(List.of(service)).
                region(region).build();


        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + digitalOceanToken);

        DigitalOceanApp createGatewayRequest = new DigitalOceanApp(appSpec, null,digitalOceanProjectId);
        HttpEntity<DigitalOceanApp> entity = new HttpEntity<>(createGatewayRequest, headers);


        try {
            ResponseEntity<DigitalOceanApp> result = restTemplate
                    .postForEntity(digitalOceanUrl, entity, DigitalOceanApp.class);

            return result.getBody();
        }
        catch(Exception e) {
            log.error("Error creating app service - {}", e);
            throw new DeploymentException("Failed to create Gateway app service", e);
        }

    }

    public void delete() {

    }
}
