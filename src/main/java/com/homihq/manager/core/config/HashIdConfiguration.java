package com.homihq.manager.core.config;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashIdConfiguration {

    @Value("${hash.pepper}")
    private String HASH_PEPPER;

    @Bean
    public Hashids hashids() {
        return new Hashids(HASH_PEPPER, 8);
    }
}
