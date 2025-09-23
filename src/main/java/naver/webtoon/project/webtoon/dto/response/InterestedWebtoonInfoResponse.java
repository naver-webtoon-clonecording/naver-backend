package naver.webtoon.project.webtoon.dto.response;

import lombok.Builder;
import lombok.Getter;
import naver.webtoon.project.common.time.TimeConverter;
import naver.webtoon.project.webtoon.entity.InterestedWebtoon;
import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.entity.enums.SerializedStatus;

import java.util.Optional;

@Getter
public class InterestedWebtoonInfoResponse {
    private Long interestedWebtoonId;
    private Long webtoonId;
    private String title;
    private String author;
    private String thumbnail;
    private String serializedStatus;
    private Integer likeCount;
    private Integer totalViewCount;
    private String updatedAt;

    @Builder
    public InterestedWebtoonInfoResponse(Long interestedWebtoonId,
                                        Long webtoonId,
                                        String title,
                                        String author,
                                        String thumbnail,
                                        String serializedStatus,
                                        Integer likeCount,
                                        Integer totalViewCount,
                                        String updatedAt){
        this.interestedWebtoonId = interestedWebtoonId;
        this.webtoonId = webtoonId;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.serializedStatus = serializedStatus;
        this.likeCount = likeCount;
        this.totalViewCount = totalViewCount;
        this.updatedAt = updatedAt;
    }

    public static InterestedWebtoonInfoResponse toResponse(InterestedWebtoon interestedWebtoon){
        Webtoon webtoon = interestedWebtoon.getWebtoon();

        SerializedStatus serializedStatus = SerializedStatus.toEnum(String.valueOf(webtoon.getSerializedStatus()));
        String convertUpdateAt = Optional.ofNullable(webtoon.getUpdatedAt())
                .map(TimeConverter::toStringFormat)
                .orElse(null);

        return InterestedWebtoonInfoResponse.builder()
                .interestedWebtoonId(interestedWebtoon.getId())
                .webtoonId(webtoon.getId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor().getName())
                .thumbnail(webtoon.getThumbnail())
                .serializedStatus(String.valueOf(serializedStatus))
                .likeCount(webtoon.getLikeCount())
                .totalViewCount(webtoon.getTotalViewCount())
                .updatedAt(convertUpdateAt)
                .build();

    }
}
