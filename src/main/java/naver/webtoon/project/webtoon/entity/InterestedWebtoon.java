package naver.webtoon.project.webtoon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.common.time.Timestamped;
import naver.webtoon.project.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestedWebtoon extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interested_webtoon_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public InterestedWebtoon(Long id, Webtoon webtoon, Member member){
        this.id = id;
        this.webtoon = webtoon;
        this.member = member;
    }

    public static InterestedWebtoon updateInterestedWebtoon(Member member, Webtoon webtoon) {
        return InterestedWebtoon.builder()
                .webtoon(webtoon)
                .member(member)
                .build();
    }
}
