package com.homihq.manager.core;

import com.homihq.manager.core.domain.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}
