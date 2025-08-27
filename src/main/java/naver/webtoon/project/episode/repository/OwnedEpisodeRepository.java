package naver.webtoon.project.episode.repository;

import naver.webtoon.project.episode.entity.Episode;
import naver.webtoon.project.episode.entity.OwnedEpisode;
import naver.webtoon.project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnedEpisodeRepository extends JpaRepository<OwnedEpisode, Long> {
    Optional<OwnedEpisode> findByMemberIdAndEpisodeId(Long memberId, Long episodeId);

    boolean existsByMemberAndEpisode(Member member, Episode episode);

    List<OwnedEpisode> findByMemberId(Long id);
}
