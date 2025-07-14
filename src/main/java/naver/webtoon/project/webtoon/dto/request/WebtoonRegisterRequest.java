package naver.webtoon.project.webtoon.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.webtoon.entity.*;
import naver.webtoon.project.webtoon.entity.enums.SerializedStatus;

import java.util.List;

@Getter
@NoArgsConstructor
public class WebtoonRegisterRequest {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String description;
    @NotNull
    private String thumbnail;
    @NotNull
    private String serializedStatus;
    @NotNull
    private List<String> publishingDay;
    private List<String> hashTag;

    public WebtoonRegisterRequest(String title, String author, String description, String thumbnail, String serializedStatus, List<String> publishingDay, List<String> hashTag) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.thumbnail = thumbnail;
        this.serializedStatus = serializedStatus;
        this.publishingDay = publishingDay;
        this.hashTag = hashTag;
    }

    @Builder
    public Webtoon toWebtoon(Author author){
        SerializedStatus serializedStatusEnum = SerializedStatus.toEnum(serializedStatus);

        return Webtoon.builder()
                .title(title)
                .author(author)
                .description(description)
                .thumbnail(thumbnail)
                .serializedStatus(serializedStatusEnum)
                .build();
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
