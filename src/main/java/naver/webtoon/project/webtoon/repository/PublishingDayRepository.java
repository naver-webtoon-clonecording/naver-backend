package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.webtoon.entity.PublishingDay;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublishingDayRepository extends JpaRepository<PublishingDay, Long> {
    Optional<PublishingDay> findByDayOfTheWeek(DayOfTheWeek dayOfTheWeekEnum);
}
