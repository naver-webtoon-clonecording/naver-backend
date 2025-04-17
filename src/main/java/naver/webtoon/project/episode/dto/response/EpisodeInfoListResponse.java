package naver.webtoon.project.episode.dto.response;

import lombok.Getter;
import naver.webtoon.project.episode.entity.Episode;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EpisodeInfoListResponse {
    private List<EpisodeInfo> episodes;

    public EpisodeInfoListResponse(List<EpisodeInfo> episodes){
        this.episodes = episodes;
    }

    public static EpisodeInfoListResponse toResponse(List<Episode> episodes){
        List<EpisodeInfo> episodeInfos = episodes.stream()
                .map(EpisodeInfo::toList)
                .collect(Collectors.toList());

        return new EpisodeInfoListResponse(episodeInfos);
    }


}
