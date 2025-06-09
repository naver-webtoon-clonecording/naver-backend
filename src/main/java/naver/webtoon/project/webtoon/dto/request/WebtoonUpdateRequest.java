package naver.webtoon.project.webtoon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.webtoon.entity.*;
import naver.webtoon.project.webtoon.entity.enums.SerializedStatus;

import java.util.List;

@Getter
@NoArgsConstructor
public class WebtoonUpdateRequest {

    private String title;
    private String author;
    private String description;
    private String thumbnail;
    private String serializedStatus;
    private List<String> publishingDay;
    private List<String> hashTag;

    public WebtoonUpdateRequest(String title, String author, String description, String thumbnail, String serializedStatus, List<String> publishingDay, List<String> hashTag) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.thumbnail = thumbnail;
        this.serializedStatus = serializedStatus;
        this.publishingDay = publishingDay;
        this.hashTag = hashTag;
    }

    public WebtoonPublishingDay toWebtoonPublishingDay(Webtoon webtoon, PublishingDay publishingDay) {
        return WebtoonPublishingDay.builder()
                .webtoon(webtoon)
                .publishingDay(publishingDay)
                .build();
    }

    public WebtoonHashTag toWebtoonHashTag(Webtoon webtoon, HashTag hashTag) {
        return WebtoonHashTag.builder()
                .webtoon(webtoon)
                .hashTag(hashTag)
                .build();
    }
}
