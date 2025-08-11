package naver.webtoon.project.webtoon.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.common.time.Timestamped;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;

@Entity
@Getter
@NoArgsConstructor
public class WebtoonPublishingDay extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "webtoon_publishing_day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publishing_day_id")
    private PublishingDay publishingDay;

    @Builder
    public WebtoonPublishingDay(Webtoon webtoon, PublishingDay publishingDay){
        this.webtoon = webtoon;
        this.publishingDay = publishingDay;
    }

    public static WebtoonPublishingDay updateWebtoonPublishingDay(Webtoon webtoon, PublishingDay publishingDay){
        return WebtoonPublishingDay.builder()
                .webtoon(webtoon)
                .publishingDay(publishingDay)
                .build();
    }
}
