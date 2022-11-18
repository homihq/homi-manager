package com.homihq.manager.core.handler;


import com.homihq.manager.core.event.UserRegisteredEvent;
import com.homihq.manager.core.mail.AwsSesService;
import com.homihq.manager.core.mail.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegisteredEventHandler {

    private final AwsSesService awsSesService;

    @EventListener
    @Async
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        log.info("Handling user registered event - {}", userRegisteredEvent);

        Mail mail = new Mail();
        mail.setFrom("homi@homihq.com");
        mail.setTo(userRegisteredEvent.getUser().getEmail());
        mail.setTemplateName("email-confirmation");
        mail.setSubject("HOMI : Please verify your signup.");
        mail.setModel(userRegisteredEvent.toMap());

        awsSesService.sendEmail(mail);
    }
}
