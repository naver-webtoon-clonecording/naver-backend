package naver.webtoon.project.common.redis.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.episode.entity.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private final StringRedisTemplate redisTemplate;

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
        redisTemplate.opsForHash().increment(title, paymentType.name(),1);
        redisTemplate.opsForZSet().incrementScore("views: " + paymentType.name(), title, 1);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        Duration duration = Duration.between(now, midnight);

        redisTemplate.expire(title,duration);
        redisTemplate.expire("views: " + paymentType.name(), duration);
    }

    public List<String> getDailyBestWebtoonTitleForAllPaymentType() {
        /**
         * 일일 조회 베스트는 최대 10개 저장된다.
         * @return List<String> titles = ["제일 많이 조회된 소설제목", "두번째로 많이 조회된 소설제목", ...., "열 번째로 많이 조회된 소설제목"]
         */
        Set<String> titles = Optional.ofNullable(redisTemplate.keys("*")).orElse(Collections.emptySet());
        Map<String, Double> scores = new HashMap<>();

        for (String title : titles){
            if(redisTemplate.type(title) != DataType.HASH){
                continue;
            }

            Map<Object, Object> views = redisTemplate.opsForHash().entries(title);
            double totalViews = views.values().stream()
                    .mapToDouble(value -> Double.parseDouble(value.toString()))
                    .sum();
            scores.put(title, totalViews);
        }

        List<String> dailyBestsForAllPaymentType = scores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return dailyBestsForAllPaymentType;
    }

    public List<String> getDailyBestWebtoonTitleForPaid() {
        Set<ZSetOperations.TypedTuple<String>> dailyBestForFree = redisTemplate.opsForZSet().reverseRangeWithScores("views: PAID", 0, 9);

        if(dailyBestForFree == null){
            return Collections.emptyList();
        }

        return dailyBestForFree.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
    }

    public List<String> getDailyBestWebtoonTitleForFree() {
        Set<ZSetOperations.TypedTuple<String>> dailyBestForFree = redisTemplate.opsForZSet().reverseRangeWithScores("views: FREE" , 0, 9);

        if(dailyBestForFree == null){
            return Collections.emptyList();
        }

        return dailyBestForFree.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
    }
}
