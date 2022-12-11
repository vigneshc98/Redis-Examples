package com.vtech.redisdb.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@EnableCaching
@EnableRedisRepositories
@Configuration
public class RedisConfig {

	//establishing the connection
    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");                                            //we can also set host and port in application.properties file
        configuration.setPort(6379);
        return new JedisConnectionFactory(configuration);
    }

    //access redis server from our app we need templates(lets create bean of redis template)
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        
        
//        ===> gives "java.lang.ClassNotFoundException: com.vtech.redis.entity.Product" error.
//        template.setKeySerializer(new StringRedisSerializer());  
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
//        template.setValueSerializer(new JdkSerializationRedisSerializer());
//        template.afterPropertiesSet();
         
        
        template.setEnableTransactionSupport(true);

        return template;
    }
    
    
    
	
//	  @Bean
//	  public JedisConnectionFactory connectionFactory(){
//		  return new JedisConnectionFactory();
//	  }
//
//	  //access redis server from our app we need templates(lets create bean of redis template)
//	  @Bean
//	  @Primary
//	  public RedisTemplate<String, Object> redisTemplate(){
//	      RedisTemplate<String, Object> template = new RedisTemplate<>();
//	      template.setConnectionFactory(connectionFactory());
//	      return template;
//	  }
}
