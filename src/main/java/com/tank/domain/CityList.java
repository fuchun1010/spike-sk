package com.tank.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fuchun
 */
@Data
public class CityList {
  @JsonProperty("HeWeather6")
  private List<Basic> basics;
}
