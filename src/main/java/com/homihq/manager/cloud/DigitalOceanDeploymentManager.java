package com.homihq.manager.cloud;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    private final ObjectMapper objectMapper;

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
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


        // create headers
        HttpHeaders headers = new HttpHeaders();
// set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
// set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        log.info("Api key - {}", apiKey);

        headers.set("Authorization", "Bearer " + apiKey);

        CreateGatewayRequest createGatewayRequest = new CreateGatewayRequest(appSpec, "bff15aad-6f5a-4d55-b690-736bea656633");
        String val =
        objectMapper.writeValueAsString(createGatewayRequest);

        System.out.println(val);

        HttpEntity<CreateGatewayRequest> entity = new HttpEntity<>(createGatewayRequest, headers);

        ResponseEntity<String> result = restTemplate
                .postForEntity(digitalOceanUrl + appObject, entity, String.class);

        log.info("result - {}", result.getBody());

    }

    @Data
    @AllArgsConstructor
    private static class CreateGatewayRequest {
        @JsonProperty("spec")
        private AppSpec appSpec;
        @JsonProperty("project_id")
        private String projectId;
    }

    @Data
    private static class AppSpec {
        private List<Domain> domains;
        private String name;
        private String region;
        private List<Service> services;


    }

    @Data
    private static class Domain {
        private String domain;
        private String type;

    }

    @Data
    private static class Service {
        @JsonProperty("http_port")
        private long httpPort;
        private Image image;
        private long instanceCount;
        @JsonProperty("instance_size_slug")
        private String instanceSizeSlug;
        private String name;
        private List<Route> routes;

    }

    @Data
    private static class Image {
        private String registry;
        @JsonProperty("registry_type")
        private String registryType;
        private String repository;
        private String tag;

    }

    @Data
    private static class Route {
        private String path;

    }
}
