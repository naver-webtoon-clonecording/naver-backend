package naver.webtoon.project.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 이 클래스는 api 응답 형식이고, Controller에서 ResponseEntity 객체안에 담깁니다.
 * 아래는 형식의 예시 입니다.
 *
 * {
 * "success" :"웹툰 조회 성공",
 * "data" :
 *    {
 *    "webtoonId" : 1,
 *    "title" : "여신강림"
 *    }
 * }
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessage<T> {

    private final String success;
    private final T data;

    public SuccessMessage(String message, T data){

        this.success = message;
        this.data = data;
    }
}
