package com.homihq.manager.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GatewayRepository extends JpaRepository<Gateway, Long> {

    Page<Gateway> findByNameLikeIgnoreCaseAndDeleted(String name, boolean deleted, Pageable pageable);
    Page<Gateway> findByDeleted(boolean deleted, Pageable pageable);

    Optional<Gateway> findByGatewayKey(String gatewayKey);

}
