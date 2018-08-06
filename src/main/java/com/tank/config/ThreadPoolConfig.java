package com.tank.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author fuchun
 */
@Component
public class ThreadPoolConfig {


  public ExecutorService createExecutorService(final String threadName) {
    return new ThreadPoolExecutor(5,
        200,
        0L,
        TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(),
        this.createThreadFactory(threadName),
        new ThreadPoolExecutor.AbortPolicy()
    );
  }

  private ThreadFactory createThreadFactory(final String threadName) {
    return new ThreadFactoryBuilder().setNameFormat(threadName + " - %d").build();
  }


}
