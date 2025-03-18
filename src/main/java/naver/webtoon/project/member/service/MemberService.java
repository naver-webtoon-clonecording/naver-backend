package naver.webtoon.project.member.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.member.dto.request.MemberSignUpRequest;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static naver.webtoon.project.common.exception.ErrorCode.ALREADY_EXIST_USERNAME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberSignUpRequest request){

        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new WebtoonException(ALREADY_EXIST_USERNAME);
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Member member = request.toMember(encryptedPassword);

        memberRepository.save(member);
    }
}
