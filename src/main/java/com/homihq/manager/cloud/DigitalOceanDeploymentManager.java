package com.homihq.manager.cloud;


import com.homihq.manager.gateway.digitalocean.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
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
        Domain domain = new Domain();
        domain.setDomain(cname);
        domain.setType("PRIMARY");

        List<Domain> domains = List.of(domain);
        String name = projectName + "-app";

        AppSpec appSpec = new AppSpec();
        appSpec.setDomains(domains);
        appSpec.setName(name);
        appSpec.setRegion(region);


        Image image = new Image();
        image.setRegistry("homihq");
        image.setRegistryType("DOCKER_HUB");
        image.setRepository("micro-gateway");
        image.setTag("latest");

        Route route = new Route();
        route.setPath("/");

        Service service = new Service();
        service.setImage(image);
        service.setHttpPort(8080);
        service.setInstanceCount(1);
        service.setName("homihq-gateway");
        service.setInstanceSizeSlug("basic-xs"); //find the list of values here
        service.setRoutes(List.of(route));


        appSpec.setServices(List.of(service));

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

        DigitalOceanSpec createGatewayRequest = new DigitalOceanSpec(appSpec, null,"bff15aad-6f5a-4d55-b690-736bea656633");

        HttpEntity<DigitalOceanSpec> entity = new HttpEntity<>(createGatewayRequest, headers);

        ResponseEntity<DigitalOceanSpec> result = restTemplate
                .postForEntity(digitalOceanUrl + appObject, entity, DigitalOceanSpec.class);

        log.info("result - {}", result.getBody());

    }




}
