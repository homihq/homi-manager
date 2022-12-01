package com.homihq.manager.cloud.digitalocean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DigitalOceanApp {
    @JsonProperty("spec")
    private AppSpec appSpec;
    @JsonProperty("id")
    private String appId;
    @JsonProperty("project_id")
    private String projectId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppSpec {
        private List<Domain> domains;
        private String name;
        private String region;
        private List<Service> services;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Domain {
        private String domain;
        private String type;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        private String registry;
        @JsonProperty("registry_type")
        private String registryType;
        private String repository;
        private String tag;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Route {
        private String path;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Service {
        @JsonProperty("http_port")
        private long httpPort;
        private Image image;
        private long instanceCount;
        @JsonProperty("instance_size_slug")
        private String instanceSizeSlug;
        private String name;
        private List<Route> routes;

    }
}
