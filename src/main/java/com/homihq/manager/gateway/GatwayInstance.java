package com.homihq.manager.gateway;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GatwayInstance {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
}
