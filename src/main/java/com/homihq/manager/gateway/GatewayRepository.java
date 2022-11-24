package com.homihq.manager.gateway;

import com.homihq.manager.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {
}
