package naver.webtoon.project.webtoon.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.webtoon.dto.request.HashTagRegisterRequest;
import naver.webtoon.project.webtoon.service.HashTagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HashTagController {

    private final HashTagService hashTagService;

    @PostMapping("/hash-tag")
    public ResponseEntity<SuccessMessage<Void>> registerHashTag(@RequestBody HashTagRegisterRequest request) {
        hashTagService.registerHashTag(request);
        return new ResponseEntity<>(new SuccessMessage<>("해시태그등록성공",null), HttpStatus.CREATED);
    }
}
