package naver.webtoon.project.transaction.dto.response;

import lombok.Getter;
import naver.webtoon.project.transaction.entity.PointTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PointTransactionResponseList {

    private List<PointTransactionResponse> pointTransactionResponses;

    public PointTransactionResponseList(List<PointTransactionResponse> pointTransactionsResponse){
        this.pointTransactionResponses = pointTransactionsResponse;
    }
    public static PointTransactionResponseList toResponse(List<PointTransaction> pointTransactions) {
        List<PointTransactionResponse> responseList = pointTransactions.stream()
                .map(PointTransactionResponse::toResponse)
                .collect(Collectors.toList());

        return new PointTransactionResponseList(responseList);
    }
}
