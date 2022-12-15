package com.homihq.manager.gateway.apidef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DynamicRouteDefinitions {

    private Long version;
    private List<ApiDefinition> apiDefinitions;
}
