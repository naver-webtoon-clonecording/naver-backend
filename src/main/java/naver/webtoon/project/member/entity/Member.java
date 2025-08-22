package naver.webtoon.project.member.entity;

import jakarta.persistence.*;
import lombok.*;
import naver.webtoon.project.common.time.Timestamped;

@Entity
@Table(name = "member")
@Getter
@ToString(exclude = "memberPassword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Timestamped {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer pointAmount;

    @Column(name = "cookie_count", nullable = false)
    private Integer cookieCount;

    @Builder
    public Member(Long id, String username, String password, Integer cookieCount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cookieCount = cookieCount;
    }
    public void chargePoint(Integer amount){
        this.pointAmount += amount;
    }
}
