package naver.webtoon.project.episode.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class EpisodeUpdateRequest {

    private String title;
    private String content;
    private String postscript;
    private Boolean isPublic;
    private LocalDate freeReleaseDate;
    @Min(value = 0, message =  "필요한 쿠키 양은 0개 이상입니다.")
    private Integer neededCookieAmount;
}
