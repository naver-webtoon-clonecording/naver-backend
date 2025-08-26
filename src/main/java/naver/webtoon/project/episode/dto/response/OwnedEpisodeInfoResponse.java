package naver.webtoon.project.episode.dto.response;

import lombok.Builder;
import lombok.Getter;
import naver.webtoon.project.episode.entity.OwnedEpisode;

@Getter
public class OwnedEpisodeInfoResponse {
    private String content;
    private Integer currentReadingPage;

    @Builder
    public OwnedEpisodeInfoResponse(String content, Integer currentReadingPage){
        this.content = content;
        this.currentReadingPage = currentReadingPage;
    }

    public static OwnedEpisodeInfoResponse toRespone(OwnedEpisode ownedEpisode) {
        return OwnedEpisodeInfoResponse.builder()
                .content(ownedEpisode.getEpisode().getContent())
                .currentReadingPage(ownedEpisode.getCurrentReadingPage())
                .build();
    }
}
