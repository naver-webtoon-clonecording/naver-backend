package naver.webtoon.project.common.redis.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.episode.entity.PaymentType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    public void setValues(String title, String type, Duration duration){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(title, type, duration);
    }

    public String getValues(String title){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(title);
    }

    public void delValues(String title){
        redisTemplate.delete(title);
    }

    public void increaseDailyView(String title, PaymentType paymentType) {
        redisTemplate.opsForHash().increment(title, paymentType,1);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        Duration duration = Duration.between(now, midnight);

        redisTemplate.expire(title,duration);
    }
}
