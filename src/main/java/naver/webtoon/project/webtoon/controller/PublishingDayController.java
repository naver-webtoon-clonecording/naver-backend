package naver.webtoon.project.webtoon.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.webtoon.dto.request.PublishingDayRegisterRequest;
import naver.webtoon.project.webtoon.service.PublishingDayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PublishingDayController {

    private final PublishingDayService publishingDayService;

    @PostMapping("/publishing-day")
    public ResponseEntity<SuccessMessage<Void>> registerPublishingDay(@RequestBody PublishingDayRegisterRequest request) {
        publishingDayService.registerPublishingDay(request);
        return new ResponseEntity<>(new SuccessMessage<>("연재일등록성공",null), HttpStatus.CREATED);
    }
}
