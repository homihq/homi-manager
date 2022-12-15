package com.homihq.manager.gateway.apidef;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(of = {"id"})
public class ApiDefinition {
    private String name;
    private String id;
    private String version;

    private List<RouteDefinition> routes;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;


    public void validate() {
        if(Objects.isNull(id) || Objects.isNull(name) || Objects.isNull(version)) {
            throw new RuntimeException("Missing mandatory field - id, name, version");
        }

    }
}
