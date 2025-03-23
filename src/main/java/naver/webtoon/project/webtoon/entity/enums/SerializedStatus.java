package naver.webtoon.project.webtoon.entity.enums;

import naver.webtoon.project.common.exception.WebtoonException;

import static naver.webtoon.project.common.exception.ErrorCode.NOT_FOUND_SERIALIZED_STATUS;

public enum SerializedStatus {

    SERIALIZED,
    BREAK,
    COMPLETE,
    ;

    public static SerializedStatus toEnum(String serializedStatus) {
        return switch (serializedStatus) {
            case "SERIALIZED" -> SERIALIZED;
            case "BREAK" -> BREAK;
            case "COMPLETE" -> COMPLETE;
            default -> throw new WebtoonException(NOT_FOUND_SERIALIZED_STATUS);
        };
    }
}
