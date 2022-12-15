package com.homihq.manager.gateway.apidef;

import lombok.Data;

import java.util.*;

@Data
public class RouteDefinition {

    private String id;

    private List<PredicateDefinition> predicates = new ArrayList<>();

    private List<FilterDefinition> filters = new ArrayList<>();

    private String uri;

    private Map<String, Object> metadata = new HashMap<>();

    private int order = 0;





}
