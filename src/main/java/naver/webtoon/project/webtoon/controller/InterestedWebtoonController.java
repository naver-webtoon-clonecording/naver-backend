package naver.webtoon.project.webtoon.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.webtoon.dto.response.InterestedWebtoonInfoResponse;
import naver.webtoon.project.webtoon.dto.response.InterestedWebtoonInfoResponseList;
import naver.webtoon.project.webtoon.service.InterestedWebtoonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InterestedWebtoonController {

    private final InterestedWebtoonService interestedWebtoonService;

    //관심웹툰등록
    @PostMapping("/webtoon/{webtoonId}/interested")
    public ResponseEntity<SuccessMessage<Void>> registerInterestedWebtoon(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long webtoonId) {
        interestedWebtoonService.registerInterestedWebtoon(userDetails.getMember(), webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("관심웹툰등록성공", null), HttpStatus.OK);
    }

    //관심웹툰삭제
    @DeleteMapping("/webtoon/{webtoonId}/interested")
    public ResponseEntity<SuccessMessage<Void>> deleteInterestedWebtoon(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long webtoonId) {
        interestedWebtoonService.deleteInterestedWebtoon(userDetails.getMember(), webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("관심웹툰삭제성공", null), HttpStatus.OK);
    }

    //관심웹툰조회
    @GetMapping("/interested-webtoons")
    public ResponseEntity<SuccessMessage<InterestedWebtoonInfoResponseList>> retrieveInterestedWebtoonsByMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        InterestedWebtoonInfoResponseList response = interestedWebtoonService.retrieveInterestedWebtoonsByMember(userDetails.getMember());
        return new ResponseEntity<>(new SuccessMessage<>("관심에피소드리스트조회성공", response), HttpStatus.OK);
    }
}
