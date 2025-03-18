package naver.webtoon.project.common;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static naver.webtoon.project.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_MEMBER)
        );
        return new UserDetailsImpl(member, member.getUsername());
    }
}
