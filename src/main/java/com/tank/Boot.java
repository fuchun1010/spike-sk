package com.tank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author fuchun
 */
@SpringBootApplication
@EnableAsync
public class Boot {

  public static void main(final String[] args) {
    SpringApplication.run(Boot.class, args);
  }




}
