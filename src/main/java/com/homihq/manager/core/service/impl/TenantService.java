package com.homihq.manager.core.service.impl;


import com.homihq.manager.core.repository.TenantRepository;
import com.homihq.manager.core.service.TenantUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Service
public class TenantService implements TenantUseCase {

    private final DataSource dataSource;
    private final TenantRepository tenantRepository;

    public void initDatabase(String schema) {
        log.info("schema - {}", schema);
        Flyway flyway = Flyway.configure()
                .locations("db/migration/tenant")
                .dataSource(dataSource)
                .schemas(schema)
                .load();

        flyway.migrate();
    }


}
