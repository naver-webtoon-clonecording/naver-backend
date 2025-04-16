package naver.webtoon.project.episode.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.episode.entity.Episode;
import naver.webtoon.project.webtoon.entity.Webtoon;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class EpisodeRegisterRequest {
    private String title;
    private String content;
    private String postscript;
    private Integer views;
    private Boolean isPublic;
    private LocalDate freeReleaseDate;
    @Min(value = 0, message =  "필요한 쿠키 양은 0개 이상입니다.")
    private Integer neededCookieAmount;

    public Episode toEpisode(Webtoon webtoon){
        return Episode.builder()
                .title(title)
                .content(content)
                .postscript(postscript)
                .views(0)
                .isPublic(isPublic)
                .freeReleaseDate(freeReleaseDate)
                .neededCookieAmount(neededCookieAmount)
                .webtoon(webtoon)
                .build();
    }

}
