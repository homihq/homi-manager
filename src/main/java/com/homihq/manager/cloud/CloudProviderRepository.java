package com.homihq.manager.cloud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CloudProviderRepository extends JpaRepository<CloudProvider, Long> {

    List<CloudProvider> findAllByActive(boolean active);
}
