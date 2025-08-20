package naver.webtoon.project.transaction.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.transaction.dto.response.PointTransactionResponseList;
import naver.webtoon.project.transaction.entity.PointTransaction;
import naver.webtoon.project.transaction.repository.PointTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointTransactionService {

    private final PointTransactionRepository pointTransactionRepository;

    public PointTransactionResponseList retireCurrentMemberPointTransactions(Member member){
        List<PointTransaction> pointTransactions = pointTransactionRepository.findByMember(member);

        return PointTransactionResponseList.toResponse(pointTransactions);
    }
}
