package com.homihq.manager.gateway;

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

    @Data
    private static class FilterDefinition {

        private String name;

        private Map<String, String> args = new LinkedHashMap<>();
    }

    @Data
    public class PredicateDefinition {


        private String name;

        private Map<String, String> args = new LinkedHashMap<>();
    }

}
