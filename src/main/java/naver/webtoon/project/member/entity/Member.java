package naver.webtoon.project.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@ToString(exclude = "memberPassword")
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "cookie_count", nullable = false)
    private Integer cookieCount;

    @Builder
    public Member(Long id, String username, String password, Integer cookieCount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cookieCount = cookieCount;
    }
}
