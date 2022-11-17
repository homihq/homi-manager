package com.homihq.manager.core.mail;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSesConfiguration {


  @Bean
  public AmazonSimpleEmailService amazonSimpleEmailService(
          @Value("${aws.ses.awsAccessKey}") String awsAccessKey,
          @Value("${aws.ses.awsSecretKey}") String awsSecretKey,
          @Value("${aws.ses.region}") String awsRegion
  ) {

    return AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(
            new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                        awsAccessKey, awsSecretKey)))
        .withRegion(Regions.fromName(awsRegion))
        .build();
  }


}
