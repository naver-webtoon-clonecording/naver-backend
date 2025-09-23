package naver.webtoon.project.dailybest.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.dailybest.dto.response.DailyBestWebtoonForFreeResponseList;
import naver.webtoon.project.dailybest.dto.response.DailyBestWebtoonForPaidResponseList;
import naver.webtoon.project.dailybest.dto.response.DailyBestWebtoonResponseList;
import naver.webtoon.project.dailybest.service.DailyBestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-bests")
public class DailyBestController {
    private final DailyBestService dailyBestService;

    @GetMapping
    public ResponseEntity<SuccessMessage<DailyBestWebtoonResponseList>> retrieveDailyBestWebtoonForAllPaymentType() {
        DailyBestWebtoonResponseList response = dailyBestService.retrieveDailyBestWebtoonForAllPaymentType();
        return new ResponseEntity<>(new SuccessMessage<>("일일 베스트 웹툰 조회 성공", response), HttpStatus.OK);
    }

    @GetMapping("/paid")
    public ResponseEntity<SuccessMessage<DailyBestWebtoonForPaidResponseList>> retrieveDailyBestWebtoonForPaid() {
        DailyBestWebtoonForPaidResponseList response = dailyBestService.retrieveDailyBestWebtoonForPaid();
        return new ResponseEntity<>(new SuccessMessage<>("유료 일일 베스트 웹툰 조회 성공", response), HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<SuccessMessage<DailyBestWebtoonForFreeResponseList>> retrieveDailyBestWebtoonForFree() {
        DailyBestWebtoonForFreeResponseList response = dailyBestService.retrieveDailyBestWebtoonForFree();
        return new ResponseEntity<>(new SuccessMessage<>("무료 일일 베스트 웹툰 조회 성공", response), HttpStatus.OK);
    }
}
