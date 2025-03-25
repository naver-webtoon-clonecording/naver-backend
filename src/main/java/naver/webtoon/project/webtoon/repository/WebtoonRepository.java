package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
}
