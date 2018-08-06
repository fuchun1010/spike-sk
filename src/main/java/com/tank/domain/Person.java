package com.tank.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;

@Data
@Accessors(chain = true)
public class Person {

  private String name;
  private Double salary;

  @Override
  public String toString() {
    val sb = String.format("name is %s, salary is %f", name, salary);
    return sb.toString();
  }
}
