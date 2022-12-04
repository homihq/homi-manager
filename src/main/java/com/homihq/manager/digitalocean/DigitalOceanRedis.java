package com.homihq.manager.digitalocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DigitalOceanRedis {

    private String id;
    private String name;
    private String engine;
    private String version;

    @JsonProperty("num_nodes")
    private int noNodes;
    private String region;
    private String size;

    private List<String> tags;
    @JsonProperty("private_network_uuid")
    private String privateNetworkId;
    @JsonProperty("project_id")
    private String projectId;

    private RedisConnection redisConnection;
    @JsonProperty("private_connection")
    private RedisConnection privateRedisConnection;

    private String status;

    @Data
    private static class RedisConnection {
        private String protocol;
        private String uri;
        private String host;
        private int port;
        private String user;
        private String password;
        private boolean ssl;

    }
}
