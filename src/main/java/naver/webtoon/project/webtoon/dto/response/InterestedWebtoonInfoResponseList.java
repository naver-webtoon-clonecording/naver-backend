package naver.webtoon.project.webtoon.dto.response;

import lombok.Getter;
import naver.webtoon.project.webtoon.entity.InterestedWebtoon;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InterestedWebtoonInfoResponseList {
    private List<InterestedWebtoonInfoResponse> interestedWebtoonInfoResponses;

    public InterestedWebtoonInfoResponseList(List<InterestedWebtoonInfoResponse> responses){
        this.interestedWebtoonInfoResponses = responses;
    }

    public static InterestedWebtoonInfoResponseList toResponse(List<InterestedWebtoon> interestedWebtoons){
        List<InterestedWebtoonInfoResponse> webtoonInfos = interestedWebtoons.stream()
                .map(InterestedWebtoonInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new InterestedWebtoonInfoResponseList(webtoonInfos);
    }
}
