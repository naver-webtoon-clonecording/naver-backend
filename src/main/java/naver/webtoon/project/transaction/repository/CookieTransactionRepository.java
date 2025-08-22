package naver.webtoon.project.transaction.repository;

import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.transaction.entity.CookieTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CookieTransactionRepository extends JpaRepository<CookieTransaction, Long> {
    List<CookieTransaction> findByMember(Member member);
}
