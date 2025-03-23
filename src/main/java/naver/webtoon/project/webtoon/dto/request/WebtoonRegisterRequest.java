package naver.webtoon.project.webtoon.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.entity.enums.SerializedStatus;

import java.util.List;

@Getter
@NoArgsConstructor
public class WebtoonRegisterRequest {

    private String title;
    private String author;
    private String description;
    private String thumbnail;
    private String serializedStatus;
    private List<String> publishingDay;
    private List<String> hashTag;

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
}
