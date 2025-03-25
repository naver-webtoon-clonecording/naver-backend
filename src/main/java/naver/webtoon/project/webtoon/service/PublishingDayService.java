package naver.webtoon.project.webtoon.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.webtoon.dto.request.PublishingDayRegisterRequest;
import naver.webtoon.project.webtoon.entity.PublishingDay;
import naver.webtoon.project.webtoon.repository.PublishingDayRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublishingDayService {

    private final PublishingDayRepository publishingDayRepository;

    @Transactional
    public void registerPublishingDay(PublishingDayRegisterRequest request) {
        PublishingDay publishingDay = request.toPublishingDay();

        publishingDayRepository.save(publishingDay);
    }
}
