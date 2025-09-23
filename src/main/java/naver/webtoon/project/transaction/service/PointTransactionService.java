package naver.webtoon.project.transaction.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.repository.MemberRepository;
import naver.webtoon.project.transaction.dto.request.PointTransactionChargeRequest;
import naver.webtoon.project.transaction.dto.response.PointTransactionResponseList;
import naver.webtoon.project.transaction.entity.PointTransaction;
import naver.webtoon.project.transaction.repository.PointTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static naver.webtoon.project.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class PointTransactionService {

    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public PointTransactionResponseList retireCurrentMemberPointTransactions(Member member){
        List<PointTransaction> pointTransactions = pointTransactionRepository.findByMember(member);

        return PointTransactionResponseList.toResponse(pointTransactions);
    }

    @Transactional
    public void chargePoint(Member currentMember, PointTransactionChargeRequest request) {
        Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_MEMBER));

        member.chargePoint(request.getAmount());
        PointTransaction pointTransaction = request.toPointTransaction(member);
        pointTransactionRepository.save(pointTransaction);
    }
}
