package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    @Query("SELECT wpd.webtoon FROM WebtoonPublishingDay wpd " +
            "JOIN wpd.publishingDay pd " +
            "WHERE pd.dayOfTheWeek = :dayOfTheWeek " +
            "AND (wpd.webtoon.serializedStatus = 'BREAK' " +
            "OR wpd.webtoon.serializedStatus = 'SERIALIZED')")
    List<Webtoon> findOnGoingWebtoonByDayOfTheWeek(@Param("dayOfTheWeek")DayOfTheWeek dayOfTheWeek);

    @Query("SELECT wpd.webtoon FROM WebtoonPublishingDay wpd " +
            "JOIN wpd.publishingDay pd " +
            "WHERE pd.dayOfTheWeek = :dayOfTheWeek " +
            "AND (wpd.webtoon.serializedStatus = 'BREAK' " +
            "OR wpd.webtoon.serializedStatus = 'SERIALIZED')"+
            "ORDER BY wpd.webtoon.updatedAt DESC")
    List<Webtoon> findLastUpdatedWebtoonsByDayOfTheWeek(@Param("dayOfTheWeek")DayOfTheWeek dayOfTheWeek);

    @Query("SELECT wt FROM Webtoon wt " +
            "WHERE wt.serializedStatus = 'COMPLETE' ")
    List<Webtoon> findPopularWebtoonsByComplete();
}
