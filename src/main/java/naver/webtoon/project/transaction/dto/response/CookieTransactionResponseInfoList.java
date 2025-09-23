package naver.webtoon.project.transaction.dto.response;

import lombok.Getter;
import naver.webtoon.project.transaction.entity.CookieTransaction;
import naver.webtoon.project.transaction.entity.PointTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CookieTransactionResponseInfoList {

    private List<CookieTransactionResponseInfo> cookieTransactionResponses;

    public CookieTransactionResponseInfoList(List<CookieTransactionResponseInfo> responses){
        this.cookieTransactionResponses = responses;
    }
    public static CookieTransactionResponseInfoList toResponse(List<CookieTransaction> cookieTransactions) {
        List<CookieTransactionResponseInfo> responseList = cookieTransactions.stream()
                .map(CookieTransactionResponseInfo::toResponse)
                .collect(Collectors.toList());

        return new CookieTransactionResponseInfoList(responseList);
    }
}
