package naver.webtoon.project.transaction.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.transaction.dto.request.CookieTransactionChargeRequest;
import naver.webtoon.project.transaction.dto.request.PointTransactionChargeRequest;
import naver.webtoon.project.transaction.dto.response.PointTransactionResponseList;
import naver.webtoon.project.transaction.service.CookieTransactionService;
import naver.webtoon.project.transaction.service.PointTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cookie-transactions")
public class CookieTransactionController {

    private final CookieTransactionService cookieTransactionService;

    @PostMapping
    public ResponseEntity<SuccessMessage<Void>> chargeCookie(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @RequestBody CookieTransactionChargeRequest request){
        cookieTransactionService.chargeCookie(userDetails.getMember(), request);
        return new ResponseEntity<>(new SuccessMessage<>("쿠키 충전 성공", null), HttpStatus.CREATED);
    }

    /*@GetMapping
    public ResponseEntity<SuccessMessage<PointTransactionResponseList>> retireCurrentMemberPointTransactions(@AuthenticationPrincipal UserDetailsImpl userDetails){
        PointTransactionResponseList response = pointTransactionService.retireCurrentMemberPointTransactions(userDetails.getMember());
        return new ResponseEntity<>(new SuccessMessage<>("포인트 거래 기록 조회 성공", response), HttpStatus.OK);
    }*/
}
