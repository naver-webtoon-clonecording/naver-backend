package naver.webtoon.project.webtoon.dto.response;

import lombok.Builder;
import lombok.Getter;
import naver.webtoon.project.common.time.TimeUtils;
import naver.webtoon.project.webtoon.entity.Webtoon;

import static naver.webtoon.project.webtoon.entity.enums.SerializedStatus.BREAK;

@Getter
public class WebtoonInfo {
    private Long webtoonId;
    private String title;
    private String author;
    private String thumbnail;
    private Boolean isUpdatedToday;
    private Boolean isNew;
    private Boolean isPause;
    private Float webtoonStarRating;

    @Builder
    public WebtoonInfo(Long webtoonId, String title, String author, String thumbnail, Boolean isUpdatedToday, Boolean isNew, Boolean isPause, Float webtoonStarRating){
        this.webtoonId = webtoonId;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.isUpdatedToday = isUpdatedToday;
        this.isNew = isNew;
        this.isPause = isPause;
        this.webtoonStarRating = webtoonStarRating;
    }

    public static WebtoonInfo toList(Webtoon webtoon){
        Boolean isUpdatedToday = TimeUtils.isUpdatedWithin24Hours(webtoon.getUpdatedAt());
        Boolean isNew = TimeUtils.isNewlyRegisteredWithin30Days(webtoon.getCreatedAt());
        Boolean isPause = (webtoon.getSerializedStatus() == BREAK);
        Float webtoonStarRating = 0.0F;

        return WebtoonInfo.builder()
                .webtoonId(webtoon.getId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor().getName())
                .thumbnail(webtoon.getThumbnail())
                .isUpdatedToday(isUpdatedToday)
                .isNew(isNew)
                .isPause(isPause)
                .webtoonStarRating(webtoonStarRating)
                .build();

    }
}
