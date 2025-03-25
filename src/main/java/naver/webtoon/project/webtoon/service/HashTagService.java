package naver.webtoon.project.webtoon.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.webtoon.dto.request.HashTagRegisterRequest;
import naver.webtoon.project.webtoon.entity.HashTag;
import naver.webtoon.project.webtoon.repository.HashTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    @Transactional
    public void registerHashTag(HashTagRegisterRequest request) {
        HashTag hashTag = request.toHashTag();

        hashTagRepository.save(hashTag);
    }
}
