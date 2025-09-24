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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static naver.webtoon.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CookieTransactionService {

    private final CookieTransactionRepository cookieTransactionRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public void chargeCookie(Member currentMember, CookieTransactionChargeRequest request) {
        String lockName = "charge-cookie" + " / " + "username: " + currentMember.getUsername();
        RLock lock = redissonClient.getLock(lockName);

        try{
            boolean isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if(!isLocked){
                throw new WebtoonException(NOT_AVAILABLE_LOCK);
            }

            Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_MEMBER));

            Integer cookieAmount = request.getAmount();
            throwIfNotEnoughPoint(cookieAmount, currentMember);

            member.chargeCookie(cookieAmount);
            CookieTransaction cookieTransaction = request.toCookieTransaction(member);
            cookieTransactionRepository.save(cookieTransaction);

            PointTransaction pointTransaction = request.toPointTransaction(member);
            pointTransactionRepository.save(pointTransaction);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
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
