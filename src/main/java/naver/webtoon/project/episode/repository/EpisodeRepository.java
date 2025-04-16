package naver.webtoon.project.episode.repository;

import naver.webtoon.project.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
