package com.homihq.manager.cloud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CloudGatewayPlanRepository extends JpaRepository<CloudGatewayPlan, Long> {
    List<CloudGatewayPlan> findAllByActive(boolean active);
}
