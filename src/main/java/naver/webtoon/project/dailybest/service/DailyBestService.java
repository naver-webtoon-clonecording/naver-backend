package naver.webtoon.project.dailybest.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.redis.service.RedisService;
import naver.webtoon.project.dailybest.dto.response.DailyBestWebtoonForFreeResponseList;
import naver.webtoon.project.dailybest.dto.response.DailyBestWebtoonForPaidResponseList;
import naver.webtoon.project.dailybest.dto.response.DailyBestWebtoonResponseList;
import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.repository.WebtoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyBestService {
    private final RedisService redisService;
    private final WebtoonRepository webtoonRepository;
    @Transactional(readOnly = true)
    public DailyBestWebtoonResponseList retrieveDailyBestWebtoonForAllPaymentType() {
        List<String> webtoonTitles = redisService.getDailyBestWebtoonTitleForAllPaymentType();
        List<Webtoon> webtoons = webtoonRepository.findByTitleIn(webtoonTitles);

        return DailyBestWebtoonResponseList.toResponse(webtoons);
    }
    @Transactional(readOnly = true)
    public DailyBestWebtoonForPaidResponseList retrieveDailyBestWebtoonForPaid() {
        List<String> webtoonTitles = redisService.getDailyBestWebtoonTitleForPaid();
        List<Webtoon> webtoons = webtoonRepository.findByTitleIn(webtoonTitles);

        return DailyBestWebtoonForPaidResponseList.toResponse(webtoons);
    }

    @Transactional(readOnly = true)
    public DailyBestWebtoonForFreeResponseList retrieveDailyBestWebtoonForFree() {
        List<String> webtoonTitles = redisService.getDailyBestWebtoonTitleForFree();
        List<Webtoon> webtoons = webtoonRepository.findByTitleIn(webtoonTitles);

        return DailyBestWebtoonForFreeResponseList.toResponse(webtoons);
    }
}
