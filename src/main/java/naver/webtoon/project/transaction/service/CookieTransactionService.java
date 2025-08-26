package naver.webtoon.project.transaction.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.repository.MemberRepository;
import naver.webtoon.project.transaction.dto.request.CookieTransactionChargeRequest;
import naver.webtoon.project.transaction.dto.response.CookieTransactionResponseInfoList;
import naver.webtoon.project.transaction.entity.CookieTransaction;
import naver.webtoon.project.transaction.entity.PointTransaction;
import naver.webtoon.project.transaction.repository.CookieTransactionRepository;
import naver.webtoon.project.transaction.repository.PointTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static naver.webtoon.project.common.exception.ErrorCode.DEFICIENT_POINT;
import static naver.webtoon.project.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class CookieTransactionService {

    private final CookieTransactionRepository cookieTransactionRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void chargeCookie(Member currentMember, CookieTransactionChargeRequest request) {
        Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_MEMBER));

        Integer cookieAmount = request.getAmount();
        throwIfNotEnoughPoint(cookieAmount, currentMember);

        member.chargeCookie(cookieAmount);
        CookieTransaction cookieTransaction = request.toCookieTransaction(member);
        cookieTransactionRepository.save(cookieTransaction);

        PointTransaction pointTransaction = request.toPointTransaction(member);
        pointTransactionRepository.save(pointTransaction);
    }

    private void throwIfNotEnoughPoint(Integer cookieAmount, Member currentMember) {
        Integer essentialAmount = cookieAmount * 100;
        if(currentMember.getPointAmount() < essentialAmount){
            throw new WebtoonException(DEFICIENT_POINT);
        }
    }

    @Transactional(readOnly = true)
    public CookieTransactionResponseInfoList retireCurrentMemberCookieTransactions(Member member) {
        List<CookieTransaction> cookieTransactions = cookieTransactionRepository.findByMember(member);
        return CookieTransactionResponseInfoList.toResponse(cookieTransactions);
    }
}
