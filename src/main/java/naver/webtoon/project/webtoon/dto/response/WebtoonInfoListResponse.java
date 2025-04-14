package naver.webtoon.project.webtoon.dto.response;

import lombok.Getter;
import naver.webtoon.project.webtoon.entity.Webtoon;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WebtoonInfoListResponse {
    private List<WebtoonInfo> webtoons;

    public WebtoonInfoListResponse(List<WebtoonInfo> webtoons){
        this.webtoons = webtoons;
    }

    public static WebtoonInfoListResponse toResponse(List<Webtoon> webtoons){
        List<WebtoonInfo> webtoonInfos = webtoons.stream()
                .map(WebtoonInfo::toList)
                .collect(Collectors.toList());

        return new WebtoonInfoListResponse(webtoonInfos);
    }
}
