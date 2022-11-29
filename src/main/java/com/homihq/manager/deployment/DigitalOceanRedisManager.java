package com.homihq.manager.deployment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.homihq.manager.gateway.digitalocean.DigitalOceanSpec;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Component
public class DigitalOceanRedisManager implements CommandLineRunner {

    private final String digitalOceanUrl = "https://api.digitalocean.com/v2/databases";

    @Value("${digitalocean.token}")
    private String apiKey;

    public void create(
            String projectId,
            String name, int noOfNodes, String sizeSlug, String region) {

        log.info("Starting creation of redis");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + apiKey);


        CreateRedisRequest createRedisRequest = new CreateRedisRequest();
        createRedisRequest.name = name;
        createRedisRequest.numNodes = noOfNodes;
        createRedisRequest.size = sizeSlug;
        createRedisRequest.region = region;
        createRedisRequest.projectId = projectId;

        HttpEntity<CreateRedisRequest> entity = new HttpEntity<>(createRedisRequest, headers);

        ResponseEntity<String> result = restTemplate
                .postForEntity(digitalOceanUrl, entity, String.class);

        log.info("result - {}", result.getBody());
    }

    public void delete() {

    }

    @Override
    public void run(String... args) throws Exception {
        create("bff15aad-6f5a-4d55-b690-736bea656633", "test-redis-4-gw-essentials", 1,
                "db-s-1vcpu-1gb" , "nyc1"
        );
    }

    @Data
    private final class CreateRedisRequest{
        private String name;
        private String engine = "redis";
        @JsonProperty("num_nodes")
        private int numNodes;

        private String size;
        private String region;

        @JsonProperty("project_id")
        private String projectId;



    }


    private final class FirewallRule{
        private String type;
        private String value;
    }


}
