package naver.webtoon.project.episode.repository;

import naver.webtoon.project.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    @Query("SELECT epi FROM Episode epi " +
            "JOIN epi.webtoon web " +
            "WHERE web.id = :webtoonId ")
    List<Episode> findByWebtoonIdToAllEpisode(@Param("webtoonId")Long webtoonId);

    @Modifying
    @Query("update Episode p set p.views = p.views + 1 where p.id = :episodeId")
    int updateViews(@Param("episodeId") Long episodeId);
}
