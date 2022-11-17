package com.homihq.manager.core.mail;


import java.nio.charset.StandardCharsets;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.amazonaws.services.simpleemail.model.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class AwsSesService {


  private final AmazonSimpleEmailService emailService;
  private final SpringTemplateEngine templateEngine;



  public void sendEmail(Mail mail) {
    log.info("Sending email - {}", mail);
    try {
      // The time for request/response round trip to aws in milliseconds
      int requestTimeout = 3000;
      SendEmailRequest request = new SendEmailRequest()
              .withDestination(
                      new Destination().withToAddresses(mail.getTo()))
              .withMessage(new Message()
                      .withBody(new Body()
                              .withHtml(new Content()
                                      .withCharset(StandardCharsets.UTF_8.name()).withData(getBody(mail))))
                      .withSubject(new Content()
                              .withCharset(StandardCharsets.UTF_8.name()).withData(mail.getSubject())))
              .withSource(mail.getFrom()).withSdkRequestTimeout(requestTimeout);
      emailService.sendEmail(request);
    } catch (RuntimeException e) {
      log.error("Error occurred sending email to {} - {}", mail.getTo(), e);
    }
  }

  private String getBody(Mail mail) {
    Context context = new Context();
    context.setVariables(mail.getModel());
    String html = templateEngine.process(mail.getTemplateName(), context);
    log.debug("html - {}", html);
    return html;
  }
}
