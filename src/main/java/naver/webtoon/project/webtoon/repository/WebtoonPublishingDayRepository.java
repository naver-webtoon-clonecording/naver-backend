package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.webtoon.entity.WebtoonPublishingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonPublishingDayRepository extends JpaRepository<WebtoonPublishingDay, Long> {
    void deleteByWebtoonId(Long id);
}
