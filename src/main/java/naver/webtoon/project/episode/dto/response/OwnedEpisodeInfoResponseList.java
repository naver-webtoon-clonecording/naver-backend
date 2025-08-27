package naver.webtoon.project.episode.dto.response;

import lombok.Getter;
import naver.webtoon.project.episode.entity.OwnedEpisode;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OwnedEpisodeInfoResponseList {

    private List<OwnedEpisodeInfoResponse> ownedEpisodeInfoResponses;

    public OwnedEpisodeInfoResponseList(List<OwnedEpisodeInfoResponse> responses){
        this.ownedEpisodeInfoResponses = responses;
    }
    public static OwnedEpisodeInfoResponseList toResponse(List<OwnedEpisode> OwnedEpisodes) {
        List<OwnedEpisodeInfoResponse> responseList = OwnedEpisodes.stream()
                .map(OwnedEpisodeInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new OwnedEpisodeInfoResponseList(responseList);
    }
}
