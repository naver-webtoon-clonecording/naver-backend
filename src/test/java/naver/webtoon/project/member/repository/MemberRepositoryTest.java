package naver.webtoon.project.member.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.fixture.MemberFixture;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static naver.webtoon.project.member.fixture.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 저장 시 정상 케이스")
    @Test
    void saveMember(){
        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);
        Member expectedMember  = memberRepository.save(member);

        assertThat(expectedMember.getId()).isNotNull();
        assertThat(expectedMember.getUsername()).isEqualTo(USERNAME);
        assertThat(expectedMember.getPassword()).isEqualTo(PASSWORD);
        assertThat(expectedMember.getCookieCount()).isEqualTo(COOKIE_COUNT);
    }

    @DisplayName("회원 저장 시 이름이 없다면 예외를 반환한다.")
    @Test
    void throwIfUsernameIsNullWhenSaveMember() {
        Member member = MemberFixture.toMember(null, PASSWORD, COOKIE_COUNT);

        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("회원 저장 시 비밀번호가 없다면 예외를 반환한다.")
    @Test
    void throwIfPasswordIsNullWhenSaveMember() {
        Member member = MemberFixture.toMember(USERNAME, null, COOKIE_COUNT);

        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("회원 저장 시 쿠키 개수가 없다면 예외를 반환한다.")
    @Test
    void throwIfCookieCountIsNullWhenSaveMember() {
        Member member = MemberFixture.toMember(USERNAME, PASSWORD, null);

        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
