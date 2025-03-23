package naver.webtoon.project.webtoon.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.author.repository.AuthorRepository;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.repository.WebtoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static naver.webtoon.project.common.exception.ErrorCode.NOT_FOUND_AUTHOR;

@Service
@RequiredArgsConstructor
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public void registerWebtoon(WebtoonRegisterRequest request){
        //등록된 작가 정보가 있는지 작가 이름으로 조회
        Author author = authorRepository.findByName(request.getAuthor()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_AUTHOR));
        Webtoon webtoon = request.toWebtoon(author);

        webtoonRepository.save(webtoon);
    }
}
