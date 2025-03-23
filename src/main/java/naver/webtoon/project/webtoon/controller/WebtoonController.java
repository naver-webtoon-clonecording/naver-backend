package naver.webtoon.project.webtoon.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.service.WebtoonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WebtoonController {

    private final WebtoonService webtoonService;

    //웹툰 등록
    @PostMapping("/webtoon")
    public ResponseEntity<SuccessMessage<Void>> registerWebtoon(@RequestBody WebtoonRegisterRequest request){
        webtoonService.registerWebtoon(request);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰등록성공",null), HttpStatus.CREATED);
    }

    //웹툰 수정

    //등록된 웹툰 삭제
}
