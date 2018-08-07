package com.tank.controller;

import com.tank.config.JsonWrapper;
import com.tank.config.ThreadPoolConfig;
import com.tank.domain.CityList;
import com.tank.domain.Person;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


  @GetMapping("/chineseCities")
  public ResponseEntity fetchChineseCities() {
    log.info("cities url '{}'", this.chineseCities);
    val uri = URI.create(this.chineseCities);
    val body = CompletableFuture
        .supplyAsync(() -> uri)
        .handle((url, e) -> this.restfulTemplate.getForObject(uri, CityList.class)).exceptionally(e -> {
          log.error(e.getMessage());
          return null;
        }).join();

    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

  @GetMapping("/{limit}/fetch")
  public DeferredResult<ResponseEntity> fetch(@PathVariable("limit") int limit) {

    val location_id = 4;
    val sql = "select _name as name,_salory as salary from person where _location_id = ? limit ?";
    this.absorbData(sql, Arrays.asList(location_id, limit).toArray());

    val response = new DeferredResult<ResponseEntity>();

    response.setResult(ResponseEntity.status(HttpStatus.OK).build());
    return response;
  }

  private void absorbData(@NonNull final String sql, @NonNull final Object... parameters) {
    val pool = this.threadPoolConfig.createExecutorService("absorb mysql data pool");
    pool.execute(() -> {
      this.mysqlTemplate.query(sql, parameters, rs -> {
        val name = rs.getString("name");
        val salary = rs.getBigDecimal("salary").doubleValue();
        val person = new Person().setName(name).setSalary(salary);
        val json = JsonWrapper.obj2JsonStr(person);
        json.ifPresent(str -> this.kafkaTemplate.send("test-consume", str));

      });
    });
    pool.shutdown();

  }

  @Autowired
  private JdbcTemplate mysqlTemplate;

  @Autowired
  private ThreadPoolConfig threadPoolConfig;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private RestTemplate restfulTemplate;

  @Value("${rest.api.chineseCities}")
  private String chineseCities;
}
