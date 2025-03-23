package naver.webtoon.project.author.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.author.dto.request.AuthorRegisterRequest;
import naver.webtoon.project.author.service.AuthorService;
import naver.webtoon.project.common.response.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/author")
    public ResponseEntity<SuccessMessage<Void>> registerAuthor(@RequestBody AuthorRegisterRequest request) {
        authorService.registerAuthor(request);
        return new ResponseEntity<>(new SuccessMessage<>("작가등록성공",null), HttpStatus.CREATED);
    }
}
