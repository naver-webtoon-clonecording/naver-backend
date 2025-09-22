package naver.webtoon.project.dailybest.dto.response;

import lombok.Getter;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfo;
import naver.webtoon.project.webtoon.entity.Webtoon;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestWebtoonResponseList {
    private List<WebtoonInfo> dailyBest;

    public DailyBestWebtoonResponseList(List<WebtoonInfo> dailyBests){
        this.dailyBest = dailyBests;
    }

    public static DailyBestWebtoonResponseList toResponse(List<Webtoon> webtoons){
        List<WebtoonInfo> webtoonInfos = webtoons.stream()
                .map(WebtoonInfo::toList)
                .collect(Collectors.toList());

        return new DailyBestWebtoonResponseList(webtoonInfos);
    }


}
