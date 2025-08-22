package naver.webtoon.project.transaction.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.transaction.dto.request.PointTransactionChargeRequest;
import naver.webtoon.project.transaction.dto.response.PointTransactionResponseList;
import naver.webtoon.project.transaction.service.PointTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point-transactions")
public class PointTransactionController {

    private final PointTransactionService pointTransactionService;

    @GetMapping
    public ResponseEntity<SuccessMessage<PointTransactionResponseList>> retireCurrentMemberPointTransactions(@AuthenticationPrincipal UserDetailsImpl userDetails){
        PointTransactionResponseList response = pointTransactionService.retireCurrentMemberPointTransactions(userDetails.getMember());
        return new ResponseEntity<>(new SuccessMessage<>("포인트 거래 기록 조회 성공", response), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SuccessMessage<Void>> chargePoint(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @RequestBody PointTransactionChargeRequest request){
        pointTransactionService.chargePoint(userDetails.getMember(), request);
        return new ResponseEntity<>(new SuccessMessage<>("포인트 충전 성공", null), HttpStatus.CREATED);
    }
}
