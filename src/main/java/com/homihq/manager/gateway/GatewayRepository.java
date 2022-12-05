package com.homihq.manager.gateway;

import com.homihq.manager.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {

    public Page<Gateway> findByRedisStatus(Gateway.Status status, Pageable pageable);
}
