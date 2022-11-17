package com.homihq.manager.core.repository;


import com.homihq.manager.core.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
