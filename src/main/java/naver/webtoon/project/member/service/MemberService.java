package naver.webtoon.project.member.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.member.dto.request.MemberSignUpRequest;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(Member member){
        /*String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Member member = request.toMember(encryptedPassword);*/
        memberRepository.save(member);
    }
}
