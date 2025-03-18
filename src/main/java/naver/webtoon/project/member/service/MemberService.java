package naver.webtoon.project.member.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.common.jwt.JwtUtil;
import naver.webtoon.project.member.dto.request.MemberLoginRequest;
import naver.webtoon.project.member.dto.request.MemberSignUpRequest;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static naver.webtoon.project.common.exception.ErrorCode.*;
import static naver.webtoon.project.common.jwt.JwtUtil.AUTHORIZATION_ACCESS;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(MemberSignUpRequest request){

        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new WebtoonException(ALREADY_EXIST_USERNAME);
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Member member = request.toMember(encryptedPassword);

        memberRepository.save(member);
    }

    @Transactional
    public void login(MemberLoginRequest request, HttpServletResponse response){

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_MEMBER));

        validatePassword(request.getPassword(), member.getPassword());

        issueTokens(response, request.getUsername());
    }

    private void issueTokens(HttpServletResponse response, String username) {
        String accessToken = jwtUtil.createAccessToken(username);
        response.addHeader(AUTHORIZATION_ACCESS, accessToken);
    }

    //비밀번호 일치 확인
    private void validatePassword(String encryptedPassword, String inputPassword) {
        if(passwordEncoder.matches(encryptedPassword, inputPassword)){
            return;
        }
        throw new WebtoonException(NOT_VALID_PASSWORD);
    }
}
