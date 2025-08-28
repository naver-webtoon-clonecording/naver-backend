package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.webtoon.entity.InterestedWebtoon;
import naver.webtoon.project.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestedWebtoonRepository extends JpaRepository<InterestedWebtoon, Long> {

    Optional<InterestedWebtoon> findByMemberAndWebtoon(Member currentMember, Webtoon webtoon);

    boolean existsByMemberIdAndWebtoonId(Long memberId, Long webtoonId);

    List<InterestedWebtoon> findByMemberId(Long id);
}
