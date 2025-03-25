package naver.webtoon.project.webtoon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.webtoon.entity.HashTag;

@Getter
@NoArgsConstructor
public class HashTagRegisterRequest {

    private String name;

    public HashTag toHashTag() {
        return HashTag.builder()
                .name(name)
                .build();
    }

    public HashTagRegisterRequest(String name) {
        this.name = name;
    }
}
