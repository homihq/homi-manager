package com.homihq.manager.core.service;


import com.homihq.manager.core.domain.Tenant;

public interface TenantUseCase {

     Tenant save(String company);
     void initDatabase(String schema);
}
