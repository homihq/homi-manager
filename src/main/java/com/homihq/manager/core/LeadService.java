package com.homihq.manager.core;


import com.homihq.manager.core.domain.QuickLead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;

    @Transactional
    public void save(String email) {
        QuickLead lead = new QuickLead();
        lead.setEmail(email);

        leadRepository.save(lead);
    }
}
