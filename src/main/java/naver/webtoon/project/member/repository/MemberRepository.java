package naver.webtoon.project.member.repository;

import naver.webtoon.project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
