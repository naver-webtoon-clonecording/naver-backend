package naver.webtoon.project.member.repository;

import naver.webtoon.project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
}
