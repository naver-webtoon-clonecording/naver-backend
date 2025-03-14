package naver.webtoon.project.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.member.entity.Member;

@Getter
@NoArgsConstructor
public class MemberSignUpRequest {

    private String username;
    private String password;

    public Member toMember(String encryptedPassword){
        return Member.builder()
                .username(username)
                .password(encryptedPassword)
                .cookieCount(0)
                .build();
    }

    public MemberSignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
