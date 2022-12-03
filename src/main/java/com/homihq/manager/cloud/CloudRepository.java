package com.homihq.manager.cloud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CloudRepository extends JpaRepository<Cloud, Long> {

    List<Cloud> findAllByActive(boolean active);
}
