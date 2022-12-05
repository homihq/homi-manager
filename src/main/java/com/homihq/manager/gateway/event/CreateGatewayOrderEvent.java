package com.homihq.manager.gateway.event;

import com.homihq.manager.gateway.Gateway;
import com.homihq.manager.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateGatewayOrderEvent {

    private Gateway gateway;
    private ProductVariant db;
}
