package com.homihq.manager.api;


import com.homihq.manager.gateway.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/meta")
public class GatewayApiController {

    private final GatewayService gatewayService;

    @GetMapping
    public void getMetaWithRoutes(@RequestHeader("X-GW-KEY") String gatewayKey,
                                  @RequestHeader("X-ORG_ID") String tenantId,
                                  @RequestHeader("X-ROUTE-VERSION") Long version,
                                  @RequestHeader("X-INSTANCE-ID") String instanceId
                                  ) {

    }


}
