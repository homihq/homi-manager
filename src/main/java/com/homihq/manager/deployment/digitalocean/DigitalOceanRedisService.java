package com.homihq.manager.deployment.digitalocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.homihq.manager.digitalocean.DigitalOceanRedis;
import com.homihq.manager.deployment.DeploymentException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
class DigitalOceanRedisService {

    private final String digitalOceanUrl = "https://api.digitalocean.com/v2/databases";

    private final RestTemplate restTemplate;

    public DigitalOceanRedis create(
            String doApiKey,
            String projectId,
            String name, int noOfNodes, String sizeSlug, String region) {

        log.info("Starting creation of redis");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + doApiKey);


        CreateRedisRequest createRedisRequest = new CreateRedisRequest();
        createRedisRequest.name = name;
        createRedisRequest.numNodes = noOfNodes;
        createRedisRequest.size = sizeSlug;
        createRedisRequest.region = region;
        createRedisRequest.projectId = projectId;

        HttpEntity<CreateRedisRequest> entity = new HttpEntity<>(createRedisRequest, headers);
        try {
            ResponseEntity<CreateRedisResponse> result = restTemplate
                    .postForEntity(digitalOceanUrl, entity, CreateRedisResponse.class);

            log.info("result - {}", result.getBody());

            return result.getBody().getDigitalOceanRedis();
        }
        catch(Exception e) {
            log.error("Error creating redis database - {}", e);
            throw new DeploymentException("Failed to create Redis DB", e);
        }

    }

    public void delete() {

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

    @Data
    @NoArgsConstructor
    private static final class CreateRedisResponse {
        private DigitalOceanRedis digitalOceanRedis;
    }


    private final class FirewallRule{
        private String type;
        private String value;
    }


}
