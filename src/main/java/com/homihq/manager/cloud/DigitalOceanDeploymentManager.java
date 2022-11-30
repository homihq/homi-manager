package com.homihq.manager.cloud;


import com.homihq.manager.cloud.digitalocean.DigitalOceanApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

//@Component
@Slf4j
@RequiredArgsConstructor
public class DigitalOceanDeploymentManager implements CommandLineRunner {

    private final String digitalOceanUrl = "https://api.digitalocean.com/v2";
    private final String appObject = "/apps";

    @Value("${digitalocean.token}")
    private String apiKey;

    @Override
    public void run(String... args) throws Exception {
        String id =  UUID.randomUUID().toString();
        String cname = id + "." + "homidev.com";
        log.info("cname - {}", cname);
        String projectName = "test-"+id.substring(0,6);
        String region = "nyc";

        provision(cname, projectName, region);
    }

    private void provision(String cname, String projectName, String region) throws Exception{ //name == project name

        List<DigitalOceanApp.Domain> domains = List.of(DigitalOceanApp.Domain
                .builder().domain(cname).type("PRIMARY")
                .build());

        DigitalOceanApp.Image image = DigitalOceanApp.Image.builder()
        .registry("homihq").
        registryType("DOCKER_HUB").
        repository("micro-gateway").
        tag("latest").build();

        String name = projectName + "-app";



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


        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


        // create headers
        HttpHeaders headers = new HttpHeaders();
// set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
// set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        log.info("Api key - {}", apiKey);

        headers.set("Authorization", "Bearer " + apiKey);

        DigitalOceanApp createGatewayRequest = new DigitalOceanApp(appSpec, null,"bff15aad-6f5a-4d55-b690-736bea656633");

        HttpEntity<DigitalOceanApp> entity = new HttpEntity<>(createGatewayRequest, headers);

        ResponseEntity<DigitalOceanApp> result = restTemplate
                .postForEntity(digitalOceanUrl + appObject, entity, DigitalOceanApp.class);

        log.info("result - {}", result.getBody());

    }




}
