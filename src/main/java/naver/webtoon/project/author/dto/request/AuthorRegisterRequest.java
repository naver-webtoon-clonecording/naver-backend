package naver.webtoon.project.author.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.author.entity.Author;

@Getter
@NoArgsConstructor
public class AuthorRegisterRequest {

    private String name;

    public Author toAuthor(){
        return Author.builder()
                .name(name)
                .build();
    }
}
