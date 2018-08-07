package com.tank.config;

import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean(name = "restfulTemplate")
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    val connectTimeout = 1000 * 60 * 5;
    return restTemplateBuilder.setConnectTimeout(connectTimeout).setReadTimeout(connectTimeout).build();
  }
}
