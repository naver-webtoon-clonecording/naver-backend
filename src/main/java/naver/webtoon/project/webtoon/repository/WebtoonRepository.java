package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Integer> {

}
