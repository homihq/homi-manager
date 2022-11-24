package com.homihq.manager.gateway.digitalocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Image {
    private String registry;
    @JsonProperty("registry_type")
    private String registryType;
    private String repository;
    private String tag;

}
