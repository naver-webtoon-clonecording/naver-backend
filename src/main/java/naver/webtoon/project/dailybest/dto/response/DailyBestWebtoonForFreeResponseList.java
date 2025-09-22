package naver.webtoon.project.dailybest.dto.response;

import lombok.Getter;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfo;
import naver.webtoon.project.webtoon.entity.Webtoon;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestWebtoonForFreeResponseList {
    private List<WebtoonInfo> freeDailyBest;

    public DailyBestWebtoonForFreeResponseList(List<WebtoonInfo> freeDailyBests){
        this.freeDailyBest = freeDailyBests;
    }

    public static DailyBestWebtoonForFreeResponseList toResponse(List<Webtoon> webtoons){
        List<WebtoonInfo> responseList = webtoons.stream()
                .map(WebtoonInfo::toList)
                .collect(Collectors.toList());

        return new DailyBestWebtoonForFreeResponseList(responseList);
    }


}
