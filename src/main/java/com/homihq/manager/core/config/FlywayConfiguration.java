package com.homihq.manager.core.config;

import com.homihq.manager.core.multitenancy.TenantContext;
import com.homihq.manager.core.repository.TenantRepository;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {


    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .locations("db/migration/core")
                .dataSource(dataSource)
                .schemas(TenantContext.DEFAULT_TENANT_ID)
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    CommandLineRunner commandLineRunner(TenantRepository repository, DataSource dataSource) {
        return args -> {
            repository.findAll().forEach(t -> {
                String tenant = t.getTenantId();
                Flyway flyway = Flyway.configure()
                        .locations("db/migration/tenant")
                        .dataSource(dataSource)
                        .schemas(tenant)
                        .load();
                flyway.migrate();

            });
        };
    }
}
