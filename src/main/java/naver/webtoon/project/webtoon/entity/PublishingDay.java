package naver.webtoon.project.webtoon.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.common.time.Timestamped;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublishingDay extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publishing_day_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek;

    @Builder
    public PublishingDay(Long id, DayOfTheWeek dayOfTheWeek) {
        this.id = id;
        this.dayOfTheWeek = dayOfTheWeek;
    }
}
