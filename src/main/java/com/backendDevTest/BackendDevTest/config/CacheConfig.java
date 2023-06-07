package com.backendDevTest.BackendDevTest.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@ConditionalOnMissingBean(value = CacheManager.class)
public class CacheConfig {

    public static final String PRODUCT_ID_DETAIL_CACHE = "ProductDetailCache";
    public static final String PRODUCT_ID_SIMILAR_CACHE = "ProductSimilarCache";

    private final ProductConfig.ProductCache config;

    public CacheConfig(ProductConfig productConfig) {
        this.config = productConfig.getProductCache();
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(PRODUCT_ID_DETAIL_CACHE,
                       getTokenCacheConfiguration(this.config.getKeyPrefixProductDetail()))
                .withCacheConfiguration(PRODUCT_ID_SIMILAR_CACHE,
                        getTokenCacheConfiguration(this.config.getKeyPrefixProductSimilar()));
    }

    private RedisCacheConfiguration getTokenCacheConfiguration(String keyPrefix) {
        JdkSerializationRedisSerializer contextAwareRedisSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());

        return RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith(keyPrefix)
                .entryTtl(Duration.ofSeconds(this.config.getTtl()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(contextAwareRedisSerializer))
                .disableCachingNullValues();
    }



}

