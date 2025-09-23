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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static naver.webtoon.project.common.exception.ErrorCode.NOT_AVAILABLE_LOCK;
import static naver.webtoon.project.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class PointTransactionService {

    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;
    private final RedissonClient redissonClient;

    @Transactional(readOnly = true)
    public PointTransactionResponseList retireCurrentMemberPointTransactions(Member member){
        List<PointTransaction> pointTransactions = pointTransactionRepository.findByMember(member);

        return PointTransactionResponseList.toResponse(pointTransactions);
    }

    @Transactional
    public void chargePoint(Member currentMember, PointTransactionChargeRequest request) {
        String lockName = "charge-point" + " / " + "username: " + currentMember.getUsername();
        RLock lock = redissonClient.getLock(lockName);

        try{
            boolean isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if(!isLocked){
                throw new WebtoonException(NOT_AVAILABLE_LOCK);
            }

            Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_MEMBER));

            member.chargePoint(request.getAmount());
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
}
