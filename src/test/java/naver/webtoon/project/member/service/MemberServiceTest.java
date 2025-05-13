package naver.webtoon.project.member.service;

import jakarta.servlet.http.HttpServletResponse;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.common.jwt.JwtUtil;
import naver.webtoon.project.member.dto.request.MemberLoginRequest;
import naver.webtoon.project.member.dto.request.MemberSignUpRequest;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.fixture.MemberFixture;
import naver.webtoon.project.member.repository.MemberRepository;
import naver.webtoon.project.webtoon.entity.Webtoon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static naver.webtoon.project.member.fixture.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    MemberService memberService;
    final String ENCRYPTED_PASSWORD = "encryptedPassword";

    @DisplayName("회원 가입 시 정상 케이스")
    @Test
    public void signUp(){
        MemberSignUpRequest request = new MemberSignUpRequest(USERNAME, PASSWORD);

        //stub
        when(memberRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(ENCRYPTED_PASSWORD);

        //when
        memberService.signUp(request);

        //then
        verify(memberRepository, times(1)).existsByUsername(request.getUsername());

        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(1)).save(memberArgumentCaptor.capture());

        Member saveMember = memberArgumentCaptor.getValue();
        assertThat(saveMember.getUsername()).isEqualTo(request.getUsername());
        assertThat(saveMember.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);
    }

    @DisplayName("회원 가입 시 중복된 이름이 있다면 예외를 반환한다.")
    @Test
    public void throwIfExistDuplicatedUsernameWhenSignUp(){
        MemberSignUpRequest request = new MemberSignUpRequest(USERNAME, PASSWORD);

        when(memberRepository.existsByUsername(request.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> memberService.signUp(request))
                .isInstanceOf(WebtoonException.class);
    }

    @DisplayName("로그인 시 정상 케이스")
    @Test
    public void login(){
        MemberLoginRequest request = new MemberLoginRequest(USERNAME, PASSWORD);
        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);

        when(memberRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(request.getPassword(), member.getPassword())).thenReturn(true);
        when(jwtUtil.createAccessToken(request.getUsername())).thenReturn("token");

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        memberService.login(request, response);

        verify(response).addHeader(Mockito.eq("AccessToken"), Mockito.anyString());

    }

    @DisplayName("로그인 시 회원을 찾지 못하면 예외를 반환한다.")
    @Test
    public void throwIfCannotFoundMemberWhenLogin(){
        //로그인 객체 생성
        MemberLoginRequest request = new MemberLoginRequest(USERNAME, PASSWORD);

        //로그인 시 회원이름으로 가입되있는 회원정보 찾기 -> 반환된 값이 없음.
        when(memberRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        //response 만들기
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        //회원정보를 찾지 못하고 예외반환
        assertThatThrownBy(() -> memberService.login(request, response))
                .isInstanceOf(WebtoonException.class);
    }

    @DisplayName("로그인 시 비밀번호가 다르면 예외를 반환한다.")
    @Test
    public void throwIfDifferentPasswordWhenLogin(){
        //로그인 요청 객체 생성
        MemberLoginRequest request = new MemberLoginRequest(USERNAME, PASSWORD);

        //MemberFixture을 통해 테스트용 회원 객체 생성
        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);

        //memberRepository.findByUsername 을 통해 위에서 만든 member 객체를 반환
        when(memberRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(member));

        //평문 비밀번호와 암호화된 비밀번호를 비교했을때 false(같지 않다)로 설정
        when(passwordEncoder.matches(request.getPassword(), member.getPassword())).thenReturn(false);

        //테스트니까 mock 객체를 통해 response 생성
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        //비밀번호 오류로 로그인하지 못하고 예외반환
        assertThatThrownBy(() -> memberService.login(request, response))
                .isInstanceOf(WebtoonException.class);

    }
}
