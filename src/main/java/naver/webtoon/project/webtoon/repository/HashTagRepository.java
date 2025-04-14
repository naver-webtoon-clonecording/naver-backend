package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.webtoon.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {


    Optional<HashTag> findByName(String hashTagName);
}