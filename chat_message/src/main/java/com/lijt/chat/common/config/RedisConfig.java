package com.lijt.chat.common.config;

import com.lijt.chat.websocket.config.RedisMessageListener;
import com.lijt.chat.websocket.config.WebSocketSessionConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * <p>类  名：com.lijt.chat.common.config.RedisConfig</p>
 * <p>类描述：todo redis配置</p>
 * <p>创建时间：2023/3/11 11:37</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    private final RedisTemplate redisTemplate;

    public RedisConfig(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置缓存管理器
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 20分钟缓存失效
                .entryTtl(Duration.ofSeconds(60))
                // 设置key的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class)))
                // 不缓存null值
                .disableCachingNullValues();
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    /**
     * redis 订阅发布
     * @param factory
     * @param listener
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, RedisMessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(listener, new ChannelTopic(WebSocketSessionConfig.CHANNEL_NAME));
        return container;
    }
}
