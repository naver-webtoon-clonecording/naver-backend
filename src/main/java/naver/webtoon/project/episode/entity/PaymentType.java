package naver.webtoon.project.episode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {

    PAID("paid"),
    FREE("free")
    ;

    private final String name;
}
