package com.vtech.redisdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class RedisConfig {

//  @Bean
//  public LettuceConnectionFactory connectionFactory(){
//	  return new LettuceConnectionFactory();
//  }
  @Bean
  public JedisConnectionFactory connectionFactory(){
	  return new JedisConnectionFactory();                 //we have set host and port in application.properties file
  }

  //access redis server from our app we need templates(lets create bean of redis template)
  @Bean
  @Primary
  public RedisTemplate<String, Object> redisTemplate(){
      RedisTemplate<String, Object> template = new RedisTemplate<>();
      template.setConnectionFactory(connectionFactory());
      return template;
  }
}