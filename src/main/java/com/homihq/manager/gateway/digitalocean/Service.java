package com.homihq.manager.gateway.digitalocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Service {
    @JsonProperty("http_port")
    private long httpPort;
    private Image image;
    private long instanceCount;
    @JsonProperty("instance_size_slug")
    private String instanceSizeSlug;
    private String name;
    private List<Route> routes;

}
