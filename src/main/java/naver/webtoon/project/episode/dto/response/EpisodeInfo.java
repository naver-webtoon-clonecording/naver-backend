package naver.webtoon.project.episode.dto.response;

import lombok.Builder;
import lombok.Getter;
import naver.webtoon.project.common.time.TimeUtils;
import naver.webtoon.project.episode.entity.Episode;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfo;
import naver.webtoon.project.webtoon.entity.Webtoon;

import java.time.LocalDate;

@Getter
public class EpisodeInfo {
    private Long episodeId;
    private String title;
    private String content;
    private String postscript;
    private Integer views;
    private Boolean isPublic;
    private Integer neededCookieAmount;
    private LocalDate freeReleaseDate;
    private Boolean isUpdatedToday;
    private Boolean isNew;

    @Builder
    public EpisodeInfo(Long episodeId, String title, String content, String postscript, Integer views, Boolean isPublic, Integer neededCookieAmount, LocalDate freeReleaseDate, Boolean isUpdatedToday, Boolean isNew){
        this.episodeId = episodeId;
        this.title = title;
        this.content = content;
        this.postscript = postscript;
        this.views = views;
        this.isPublic = isPublic;
        this.neededCookieAmount = neededCookieAmount;
        this.freeReleaseDate = freeReleaseDate;
        this.isUpdatedToday = isUpdatedToday;
        this.isNew = isNew;
    }

    public static EpisodeInfo toEpisode(Episode episode){
        return new EpisodeInfo(
                episode.getId(),
                episode.getTitle(),
                episode.getContent(),
                episode.getPostscript(),
                episode.getViews(),
                episode.getIsPublic(),
                episode.getNeededCookieAmount(),
                episode.getFreeReleaseDate(),
                TimeUtils.isUpdatedWithin24Hours(episode.getUpdatedAt()),
                TimeUtils.isNewlyRegisteredWithin30Days(episode.getCreatedAt())
        );
    }

    public static EpisodeInfo toList(Episode episode){
        Boolean isUpdatedToday = TimeUtils.isUpdatedWithin24Hours(episode.getUpdatedAt());
        Boolean isNew = TimeUtils.isNewlyRegisteredWithin30Days(episode.getCreatedAt());

        return EpisodeInfo.builder()
                .episodeId(episode.getId())
                .title(episode.getTitle())
                .content(episode.getContent())
                .postscript(episode.getPostscript())
                .views(episode.getViews())
                .isPublic(episode.getIsPublic())
                .neededCookieAmount(episode.getNeededCookieAmount())
                .freeReleaseDate(episode.getFreeReleaseDate())
                .isUpdatedToday(isUpdatedToday)
                .isNew(isNew)
                .build();

    }
}
