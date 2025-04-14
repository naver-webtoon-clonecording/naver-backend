package naver.webtoon.project.webtoon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.webtoon.entity.PublishingDay;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;

@Getter
@NoArgsConstructor
public class PublishingDayRegisterRequest {

    private DayOfTheWeek dayOfTheWeek;

    public PublishingDay toPublishingDay() {
        return PublishingDay.builder()
                .dayOfTheWeek(dayOfTheWeek)
                .build();
    }

    public PublishingDayRegisterRequest(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }
}
