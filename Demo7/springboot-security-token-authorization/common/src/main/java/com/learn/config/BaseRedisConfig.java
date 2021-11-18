package com.learn.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import com.learn.service.RedisService;
import com.learn.service.impl.RedisServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis基础配置
 * Created by macro on 2020/6/19.
 */
public class BaseRedisConfig {
    /**
     * RedisSerializer
     * 自定义序列化类参照：https://docs.spring.io/spring-data/redis/docs/current/api/
     * GenericJackson2JsonRedisSerializer - 基于通用Jackson的2 RedisSerializer，objects使用动态类型映射到JSON。
     * GenericToStringSerializer          - 通用字符串转为byte []（并返回）序列化器。依靠SpringConversionService将对象转换为String，反之亦然。使用指定的字符集（默认为UTF-8）将字符串转换为字节，反之亦然。注意：如果将类定义为Spring bean，则转换服务初始化会自动发生。注意：不会以任何特殊方式处理null，将所有内容委派给容器。
     * Jackson2JsonRedisSerializer        - 该转换器可用于绑定到类型化的bean或未类型化的HashMap实例。 注意：空对象被序列化为空数组，反之亦然。
     * JdkSerializationRedisSerializer    - Java序列化Redis序列化器。代表默认值（基于Java）serializer和 DefaultDeserializer。这serializer可以用customClassLoader或own构造 converters。
     * OxmSerializer                      - Spring的O / X映射之上的Serializer适配器。将序列化/反序列化委托给OXMMarshaller 和Unmarshaller。注意：空对象被序列化为空数组，反之亦然。
     * StringRedisSerializer              - 简单String到byte []（和返回）串行器。Strings 使用指定的字符集（默认为UTF-8）转换为字节，反之亦然。与Redis的交互主要通过字符串进行时很有用。由于空字符串是有效的键/值，因此不执行任何null转换。
     */
    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //必须设置，否则无法将JSON转化为对象，会转化成Map类型
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<Object> serializer = redisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置Redis缓存有效期为1天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer())).entryTtl(Duration.ofDays(1));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }


    @Bean
    public RedisService redisService() {
        return new RedisServiceImpl();
    }

}
