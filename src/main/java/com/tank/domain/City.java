package com.tank.domain;

import lombok.Data;

/**
 * @author fuchun
 */
@Data
public class City {

  private String cid;
  private String location;
  private String parent_city;
  private String admin_area;
  private String cnty;
  private String lat;
  private String lon;
  private String tz;
  private String type;
}
