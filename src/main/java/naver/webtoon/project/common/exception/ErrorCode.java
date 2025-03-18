package naver.webtoon.project.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_VALID_FORMAT(HttpStatus.BAD_REQUEST, "NOT_VALID_FORMAT", "지정된 양식을 사용해주세요."),

    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "JWT_001", "Access Token이 유효하지 않습니다."),
    EXPIRATION_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_002", "Access Token이 만료되었습니다"),
    ACCESS_TOKEN_NOT_SUPPORT(HttpStatus.UNAUTHORIZED, "JWT_003", "지원하지 않는 Access Token입니다"),
    UNKNOWN_ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "JWT_004", "Access Token 에러입니다"),
    UNKNOWN_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "JWT_005", "알 수 없는 토큰 에러입니다"),

    NOT_AUTHORIZED_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER_001", "인가되지 않은 사용자입니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "MEMBER_002", "찾을 수 없는 회원입니다."),
    NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_003", "비밀번호를 다시 확인해주세요."),
    ALREADY_EXIST_USERNAME(HttpStatus.BAD_REQUEST, "MEMBER_004", "이미 존재하는 회원 아이디입니다."),

    NOT_FOUND_WEBTOON(HttpStatus.NOT_FOUND, "WEBTOON_001", "찾을 수 없는 웹툰입니다."),
    NOT_FOUND_SERIALIZED_STATUS(HttpStatus.NOT_FOUND, "WEBTOON_002", "찾을 수 없는 연재상태입니다."),

    NOT_FOUND_AUTHOR(HttpStatus.NOT_FOUND, "AUTHOR_001", "찾을 수 없는 작가입니다."),

    NOT_FOUND_HASH_TAG(HttpStatus.NOT_FOUND, "HASH_TAG_001", "찾을 수 없는 해시태그입니다."),

    NOT_FOUND_PUBLISHING_DAY(HttpStatus.NOT_FOUND, "PUBLISHING_DAY_001", "찾을 수 없는 연재일입니다."),
    NOT_FOUND_DAY_OF_THE_WEEK(HttpStatus.NOT_FOUND, "PUBLISHING_DAY_002", "찾을 수 없는 요일입니다."),

    NOT_FOUND_WEBTOON_PUBLISHING_DAY(HttpStatus.NOT_FOUND, "WEBTOON_PUBLISHING_DAY_002", "찾을 수 없는 웹툰연재요일입니다."),

    NOT_FOUND_EPISODE(HttpStatus.NOT_FOUND, "EPISODE_001", "찾을 수 없는 에피소드입니다."),
    PRIVATE_EPISODE_MUST_BE_PAID(HttpStatus.BAD_REQUEST, "EPISODE_002", "비공개 에피소드는 유료여야 합니다."),
    PUBLIC_EPISODE_MUST_BE_FREE(HttpStatus.BAD_REQUEST, "EPISODE_003", "공개 에피소드는 무료여야 합니다."),
    FREE_EPISODE_MUST_HAVE_FREE_RELEASE_DATE_IS_NULL(HttpStatus.BAD_REQUEST, "EPISODE_004", "공개 웹툰은 무료 공개일이 입력되지 않아야 합니다."),
    FREE_RELEASE_DATE_MUST_BE_AFTER_THAN_CURRENT_DATE(HttpStatus.BAD_REQUEST, "EPISODE_005", "무료 공개일은 현재 날짜보다 이후여야 합니다."),
    PRIVATE_EPISODE_INACCESSIBILITY(HttpStatus.BAD_REQUEST, "EPISODE_006", "비공개 에피소드는 접근 불가능합니다."),
    PUBLIC_EPISODE_INACCESSIBILITY(HttpStatus.BAD_REQUEST, "EPISODE_007", "공개 에피소드는 접근 불가능합니다."),

    ;



    private final HttpStatus httpStatus;
    private final String code;
    private final String error;
}
