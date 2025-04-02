package naver.webtoon.project.webtoon.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.webtoon.service.InterestedWebtoonService;
import naver.webtoon.project.webtoon.service.WebtoonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
