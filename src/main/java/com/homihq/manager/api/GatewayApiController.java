package com.homihq.manager.api;


import com.homihq.manager.gateway.GatewayService;
import com.homihq.manager.gateway.apidef.ApiDefinition;
import com.homihq.manager.gateway.apidef.DynamicRouteDefinitions;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meta")
@Slf4j
public class GatewayApiController {

    private final GatewayService gatewayService;

    @GetMapping
    public DynamicRouteDefinitions getMetaWithRoutes(@RequestHeader("X-GW-KEY") String gatewayKey,
                                                     @RequestHeader("X-ORG_ID") String tenantId,
                                                     @RequestHeader("X-ROUTE-VERSION") Long version,
                                                     @RequestHeader("X-INSTANCE-ID") String instanceId
                                  ) {
        log.info("gatewayKey = {}", gatewayKey);
        log.info("tenantId = {}", tenantId);
        log.info("version = {}", version);
        log.info("instanceId = {}", instanceId);

        return this.gatewayService.queryApiDefinitions(gatewayKey, instanceId);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public void uploadMetaWithRoutes(
            @RequestHeader("X-GW-KEY") String gatewayKey,
            @RequestHeader("X-ORG_ID") String tenantId,
            @Valid UploadRoutesFileRequest uploadRoutesFileRequest) {

        log.info("gatewayKey - {}" , gatewayKey);
        log.info("tenantId - {}" , tenantId);
        log.info("uploadRoutesFileRequest - {}" , uploadRoutesFileRequest);

        this.gatewayService.updateRoutes(gatewayKey,  uploadRoutesFileRequest.file);
    }


    @Data
    public static class UploadRoutesFileRequest {

        @NotNull(message = "File is required.")
        private MultipartFile file;

    }



}
