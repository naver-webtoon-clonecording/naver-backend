package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.webtoon.entity.WebtoonHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonHashTagRepository extends JpaRepository<WebtoonHashTag, Long> {
    void deleteByWebtoonId(Long id);
}