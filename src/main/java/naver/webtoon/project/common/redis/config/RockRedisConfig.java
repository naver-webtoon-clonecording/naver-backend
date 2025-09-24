package naver.webtoon.project.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RockRedisConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(){
        return Redisson.create();
    }
}
