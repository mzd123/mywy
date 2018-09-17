package com.mzd.mywy.plug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.nio.charset.Charset;

@Configuration
public class RedisConfig {
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory j = new JedisConnectionFactory();
        //绑定ip
        j.setHostName("localhost");
        //监听端口
        j.setPort(6379);
        //客户端闲置多少时间后断开连接
        j.setTimeout(2000);
        j.setUsePool(true);
        j.setUseSsl(false);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        j.setPoolConfig(jedisPoolConfig);
        j.setConvertPipelineAndTxResults(true);
        return j;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer(Charset.forName("UTF8")));
        return redisTemplate;
    }

}