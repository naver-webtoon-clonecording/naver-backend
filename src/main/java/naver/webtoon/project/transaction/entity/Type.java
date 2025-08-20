package naver.webtoon.project.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {

    CHARGE("충전"),
    CONSUME("소모"),
    ;

    private final String koreanName;

    private static String toKoreanName(Type type){
        return switch(type){
            case CHARGE -> CHARGE.koreanName;
            case CONSUME -> CONSUME.koreanName;
        };
    }
}
