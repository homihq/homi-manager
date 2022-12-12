package com.homihq.manager.core.multitenancy;


import com.homihq.manager.core.domain.SimpleUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {
        
      
       @Override
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
           log.info("Pre handle");

           Optional<SimpleUser> u = SecurityUtil.getUser();

           if(u.isPresent()) {
               this.setTenantContext(u.get().getUser().getTenant().getTenantId());
           }
           else{
               log.info("Handling api call");
               String tenantId = request.getHeader("X-ORG_ID");
               this.setTenantContext(tenantId);
           }

           return true;
       }
 
       @Override
       public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
           TenantContext.clear();
       }
         
       private boolean setTenantContext(String tenant) {
         TenantContext.setCurrentTenant(tenant);
         return true;
       }
}
