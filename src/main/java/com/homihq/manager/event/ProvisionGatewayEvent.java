package com.homihq.manager.event;

import com.homihq.manager.gateway.Gateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProvisionGatewayEvent {
    private Gateway gateway;
}
