package naver.webtoon.project.transaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.common.time.Timestamped;
import naver.webtoon.project.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AutoCloseable.class)
public class Transaction extends Timestamped {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Transaction(Type type, Integer amount, Integer balance, Member member){
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.member = member;
    }

}
