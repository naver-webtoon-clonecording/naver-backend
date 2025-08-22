package naver.webtoon.project.transaction.dto.request;

import lombok.Getter;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.transaction.entity.PointTransaction;
import naver.webtoon.project.transaction.entity.Type;

import static naver.webtoon.project.transaction.entity.Type.CHARGE;

@Getter
public class PointTransactionChargeRequest {
    private Integer amount;

    public PointTransaction toPointTransaction(Member member){
        Type type = CHARGE;
        Integer balance = member.getPointAmount() + amount;

        return PointTransaction.builder()
                .amount(amount)
                .balance(balance)
                .type(type)
                .member(member)
                .build();
    }
}
