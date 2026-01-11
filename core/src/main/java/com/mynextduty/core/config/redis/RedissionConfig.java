package com.mynextduty.core.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissionConfig {

  @Bean
  public RedissonClient redissonClient(RedisProperties prop) {
    Config config = new Config();
    config.useSingleServer().setAddress("rediss://" + prop.getHost() + ":" + prop.getPort());
    log.info("Configuring redis server with address: {}", config.useSingleServer().getAddress());
    if (!prop.getUsername().isEmpty()) {
      config.useSingleServer().setUsername(prop.getUsername());
      log.info(
          "Configuring redis server with username: {}", config.useSingleServer().getUsername());
    }
    if (!prop.getPassword().isEmpty()) {
      config.useSingleServer().setPassword(prop.getPassword());
      log.info("Configuring redis server with provided password: XXXXXXXXXX");
    }
    config.useSingleServer().setDatabase(prop.getIndex());
    log.info("Configuring redis server with index: {}", config.useSingleServer().getDatabase());
    config.useSingleServer().setTimeout(prop.getTimeout());
    log.info("Configuring redis server with timeout: {}", config.useSingleServer().getTimeout());
    return Redisson.create(config);
  }
}
