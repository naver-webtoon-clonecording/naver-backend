package naver.webtoon.project.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    private String username;
    private String password;

    public MemberLoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
}
