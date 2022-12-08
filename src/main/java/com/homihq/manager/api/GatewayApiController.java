package com.homihq.manager.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class GatewayApiController {

    @PostMapping
    public void register() {

    }

    private static class RegisterGatewayRequest{

    }
}
