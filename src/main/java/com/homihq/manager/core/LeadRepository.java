package com.homihq.manager.core;

import com.homihq.manager.core.domain.QuickLead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<QuickLead, Long> {
}
