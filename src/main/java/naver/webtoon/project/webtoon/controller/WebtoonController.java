package naver.webtoon.project.webtoon.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.dto.request.WebtoonUpdateRequest;
import naver.webtoon.project.webtoon.service.WebtoonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/webtoon/{webtoonId}")
    public ResponseEntity<SuccessMessage<Void>> updateWebtoon(@PathVariable Long webtoonId, @RequestBody WebtoonUpdateRequest request) {
        webtoonService.updateWebtoon(webtoonId, request);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰수정성공",null), HttpStatus.OK);
    }

    //등록된 웹툰 삭제
}
