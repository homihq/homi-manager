package com.homihq.manager.gateway.digitalocean;


import lombok.Data;
import java.util.List;

@Data
public class AppSpec {
    private List<Domain> domains;
    private String name;
    private String region;
    private List<Service> services;


}
