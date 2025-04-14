package naver.webtoon.project.author.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.author.dto.request.AuthorRegisterRequest;
import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public void registerAuthor(AuthorRegisterRequest request){
        Author author = request.toAuthor();

        authorRepository.save(author);
    }
}
