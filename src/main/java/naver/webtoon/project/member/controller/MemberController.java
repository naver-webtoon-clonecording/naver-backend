package naver.webtoon.project.member.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.member.dto.request.MemberSignUpRequest;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.service.MemberService;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<SuccessMessage<Void>> signUp(@RequestBody Member member) {
        memberService.signUp(member);
        return new ResponseEntity<>(new SuccessMessage<>("회원가입성공",null), HttpStatus.CREATED);
    }
}
