package com.tank.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JsonWrapper {

  public static <T> Optional<String> obj2JsonStr(T t) {
    val mapper = new ObjectMapper();
    try {
      return Optional.of(mapper.writeValueAsString(t));
    } catch (JsonProcessingException e) {
      val tips = String.format("convert object to json error:%s", e.getMessage());
      log.error(tips);
      return Optional.empty();
    }
  }

  public static <T> Optional<T> jsonStr2Ojb(final String json, Class<T> classType) {
    val mapper = new ObjectMapper();
    try {
      val rs = mapper.readValue(json, classType);
      return Optional.of(rs);
    } catch (IOException e) {
      val tips = String.format("convert json to object error:%s", e.getMessage());
      log.error(tips);
      return Optional.empty();
    }
  }

}
