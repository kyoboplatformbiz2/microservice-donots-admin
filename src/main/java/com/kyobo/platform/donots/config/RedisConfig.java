//package com.kyobo.platform.donots.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//
////@Configuration
////@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
//public class RedisConfig {
//
//	@Value("${spring.redis.host}")
//	private String redisHost;
//
//	@Value("${spring.redis.port}")
//	private int redisPort;
//
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//		redisTemplate.setConnectionFactory(connectionFactory);
//		return redisTemplate;
//	}
//
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//		configuration.setHostName(redisHost);
//		configuration.setPort(redisPort);
//		return new LettuceConnectionFactory(configuration);
//	}
//}
//
//
