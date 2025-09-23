package naver.webtoon.project.transaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.member.entity.Member;

import static naver.webtoon.project.transaction.entity.Type.CONSUME;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CookieTransaction extends Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cookie_transaction_id")
    private Long id;

    @Builder
    public CookieTransaction(Type type, Integer amount, Integer balance, Member member, Long id) {
        super(type, amount, balance, member);
        this.id = id;
    }

    public static CookieTransaction createConsumeCookieTransaction(Member member, int amount) {
        Type type = CONSUME;
        Integer balance = member.getCookieCount() - amount;

        return CookieTransaction.builder()
                .type(type)
                .amount(amount)
                .balance(balance)
                .member(member)
                .build();
    }
}
