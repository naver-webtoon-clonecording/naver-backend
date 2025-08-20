package naver.webtoon.project.transaction.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.transaction.dto.response.PointTransactionResponseList;
import naver.webtoon.project.transaction.service.PointTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point-transactions")
public class PointTransactionController {

    private final PointTransactionService pointTransactionService;

    @GetMapping
    public ResponseEntity<SuccessMessage<PointTransactionResponseList>> retrieveCurrentMemberPointTransactions(UserDetailsImpl userDetails){
        PointTransactionResponseList response = pointTransactionService.retrieveCurrentMemberPointTransactions(userDetails.getMember());
        return new ResponseEntity<>(new SuccessMessage<>("쿠키 거래 기록 조회 성공", response), HttpStatus.OK);
    }
}
