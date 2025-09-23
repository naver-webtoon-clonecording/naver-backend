package naver.webtoon.project.dailybest.dto.response;

import lombok.Getter;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfo;
import naver.webtoon.project.webtoon.entity.Webtoon;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DailyBestWebtoonForPaidResponseList {
    private List<WebtoonInfo> paidDailyBest;

    public DailyBestWebtoonForPaidResponseList(List<WebtoonInfo> paidDailyBests){
        this.paidDailyBest = paidDailyBests;
    }

    public static DailyBestWebtoonForPaidResponseList toResponse(List<Webtoon> webtoons){
        List<WebtoonInfo> responseList = webtoons.stream()
                .map(WebtoonInfo::toList)
                .collect(Collectors.toList());

        return new DailyBestWebtoonForPaidResponseList(responseList);
    }


}
