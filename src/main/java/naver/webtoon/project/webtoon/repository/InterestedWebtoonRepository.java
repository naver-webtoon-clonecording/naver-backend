package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.webtoon.entity.InterestedWebtoon;
import naver.webtoon.project.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestedWebtoonRepository extends JpaRepository<InterestedWebtoon, Long> {

    boolean existsByMemberAndWebtoon(Member member, Webtoon webtoon);
}
