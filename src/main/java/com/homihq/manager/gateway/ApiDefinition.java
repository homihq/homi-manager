package com.homihq.manager.gateway;

import lombok.Data;

import java.util.List;

@Data
public class ApiDefinition {
    private String name;
    private String id;

    private List<RouteDefinition> routeDefinitions;
}
