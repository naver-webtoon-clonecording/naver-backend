package naver.webtoon.project.transaction.repository;

import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.transaction.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    List<PointTransaction> findByMember(Member member);
}
