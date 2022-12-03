package com.homihq.manager.cloud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatewayProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByActive(boolean active);
}
