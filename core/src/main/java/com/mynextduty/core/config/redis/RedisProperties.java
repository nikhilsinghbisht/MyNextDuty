package com.mynextduty.core.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "core.redis")
public class RedisProperties {
    private String host;
    private String port;
    private String username;
    private String password;
    private int index;
    private int timeout;
}
