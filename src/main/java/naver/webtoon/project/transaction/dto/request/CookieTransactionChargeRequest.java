package naver.webtoon.project.transaction.dto.request;

import lombok.Getter;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.transaction.entity.CookieTransaction;
import naver.webtoon.project.transaction.entity.PointTransaction;
import naver.webtoon.project.transaction.entity.Type;

import static naver.webtoon.project.transaction.entity.Type.CHARGE;
import static naver.webtoon.project.transaction.entity.Type.CONSUME;

@Getter
public class CookieTransactionChargeRequest {
    private Integer amount;

    public CookieTransaction toCookieTransaction(Member member){
        Type type = CHARGE;
        Integer balance = member.getPointAmount() + amount;

        return CookieTransaction.builder()
                .amount(amount)
                .balance(balance)
                .type(type)
                .member(member)
                .build();
    }

    public PointTransaction toPointTransaction(Member member){
        Type type = CONSUME;
        Integer balance = member.getPointAmount() - amount;

        return PointTransaction.builder()
                .amount(amount)
                .balance(balance)
                .type(type)
                .member(member)
                .build();
    }
}
