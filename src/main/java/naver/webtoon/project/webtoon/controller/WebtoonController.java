package naver.webtoon.project.webtoon.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.dto.request.WebtoonUpdateRequest;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfoListResponse;
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
    public ResponseEntity<SuccessMessage<Void>> registerWebtoon(@RequestBody WebtoonRegisterRequest request) {
        webtoonService.registerWebtoon(request);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰등록성공", null), HttpStatus.CREATED);
    }

    //웹툰 수정
    @PutMapping("/webtoon/{webtoonId}")
    public ResponseEntity<SuccessMessage<Void>> updateWebtoon(@PathVariable Long webtoonId, @RequestBody WebtoonUpdateRequest request) {
        webtoonService.updateWebtoon(webtoonId, request);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰수정성공", null), HttpStatus.OK);
    }

    //등록된 웹툰 삭제
    @DeleteMapping("/webtoon/{webtoonId}")
    public ResponseEntity<SuccessMessage<Void>> deleteWebtoon(@PathVariable Long webtoonId) {
        webtoonService.deleteWebtoon(webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰삭제성공", null), HttpStatus.OK);
    }

    //요일별인기순웹툰조회 -> 관심웹툰으로 등록된 수가 많은 웹툰 순위로 내림차순
    @GetMapping("/webtoon/{publishingDay}/popular")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getPopularWebtoonsByDayOfWeekAndWithin30Days(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getPopularWebtoonsByDayOfWeekAndWithin30Days(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별인기순웹툰조회성공", response), HttpStatus.OK);
    }

    //요일별업데이트순웹툰조회
    @GetMapping("/webtoon/{publishingDay}/latest-update")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getLastUpdateWebtoonsByDayOfWeek(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getLastUpdateWebtoonsByDayOfWeek(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별업데이트순웹툰조회성공", response), HttpStatus.OK);
    }

    //요일별조회수웹툰조회
    @GetMapping("/webtoon/{publishingDay}/total-views")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getTotalViewsWebtoonsByDayOfWeek(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getTotalViewsWebtoonsByDayOfWeek(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별조회순웹툰조회",response), HttpStatus.OK);
    }

    //완결웹툰인기순조회 -> 관심웹툰으로 등록된 수가 많은 웹툰 순위로 내림차순
    @GetMapping("/webtoon/finished/popular")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getPopularWebtoonsByComplete() {
        WebtoonInfoListResponse response = webtoonService.getPopularWebtoonsByComplete();
        return new ResponseEntity<>(new SuccessMessage<>("완결웹툰인기순조회성공", response), HttpStatus.OK);
    }

    //최근완결순조회
    @GetMapping("/webtoon/finished/latest")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getLatestCompletedWebtoons() {
        WebtoonInfoListResponse response = webtoonService.getLatestCompletedWebtoons();
        return new ResponseEntity<>(new SuccessMessage<>("최근완결순조회성공", response), HttpStatus.OK);
    }
}
