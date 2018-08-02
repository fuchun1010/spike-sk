package com.tank.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author fuchun
 */
@Configuration
public class MySqlDataSource {

  @Bean(name = "mysqlDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.mysql")
  public DataSource initMysqlDataSource() {
    return DataSourceBuilder.create().build();
  }


  @Bean(name = "mysqlTemplate")
  public JdbcTemplate initMysqlTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
