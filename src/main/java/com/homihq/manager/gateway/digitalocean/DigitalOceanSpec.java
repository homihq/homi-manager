package com.homihq.manager.gateway.digitalocean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DigitalOceanSpec {
    @JsonProperty("spec")
    private AppSpec appSpec;
    @JsonProperty("id")
    private String appId;
    @JsonProperty("project_id")
    private String projectId;
}
