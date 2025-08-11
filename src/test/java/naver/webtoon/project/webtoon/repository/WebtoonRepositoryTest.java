package naver.webtoon.project.webtoon.repository;

import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.author.repository.AuthorRepository;
import naver.webtoon.project.member.dto.request.MemberSignUpRequest;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.fixture.MemberFixture;
import naver.webtoon.project.member.repository.MemberRepository;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.entity.*;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;
import naver.webtoon.project.webtoon.entity.enums.SerializedStatus;
import naver.webtoon.project.webtoon.service.WebtoonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static naver.webtoon.project.member.fixture.MemberFixture.*;
import static naver.webtoon.project.webtoon.fixture.WebtoonFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
public class WebtoonRepositoryTest {
    @Autowired
    private WebtoonRepository webtoonRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublishingDayRepository publishingDayRepository;
    @Autowired
    private InterestedWebtoonRepository interestedWebtoonRepository;
    @Autowired
    private WebtoonPublishingDayRepository webtoonPublishingDayRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HashTagRepository hashTagRepository;

    @DisplayName("웹툰 저장시 정상 케이스")
    @Test
    void 웹툰_저장_정상(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        Webtoon webtoon = Webtoon.builder()
                .title(TITLE)
                .author(author)
                .description(DESCRIPTION)
                .serializedStatus(SerializedStatus.valueOf(SERIALIZEDSTATUS))
                .thumbnail(THUMBNAIL)
                .build();

        Webtoon expectedWebtoon = webtoonRepository.save(webtoon);

        assertThat(expectedWebtoon.getId()).isNotNull();
        assertThat(expectedWebtoon.getTitle()).isEqualTo(TITLE);
        assertThat(expectedWebtoon.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(expectedWebtoon.getSerializedStatus()).isEqualTo(SerializedStatus.valueOf(SERIALIZEDSTATUS));
        assertThat(expectedWebtoon.getThumbnail()).isEqualTo(THUMBNAIL);
        assertThat(expectedWebtoon.getAuthor().getName()).isEqualTo("테스트");
    }

    @DisplayName("웹툰 저장시 제목 공백 예외 반환.")
    @Test
    void 웹툰_저장시_제목_공백_예외_반환(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        Webtoon webtoon = Webtoon.builder()
                .title(null)
                .author(author)
                .description(DESCRIPTION)
                .serializedStatus(SerializedStatus.valueOf(SERIALIZEDSTATUS))
                .thumbnail(THUMBNAIL)
                .build();

        assertThatThrownBy(() -> webtoonRepository.save(webtoon))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("웹툰 저장시 설명 공백 예외 반환.")
    @Test
    void 웹툰_저장시_설명_공백_예외_반환(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        Webtoon webtoon = Webtoon.builder()
                .title(TITLE)
                .author(author)
                .description(null)
                .serializedStatus(SerializedStatus.valueOf(SERIALIZEDSTATUS))
                .thumbnail(THUMBNAIL)
                .build();

        assertThatThrownBy(() -> webtoonRepository.save(webtoon))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("웹툰 저장시 진행 상태 공백 예외 반환.")
    @Test
    void 웹툰_저장시_진행_상태_공백_예외_반환(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        Webtoon webtoon = Webtoon.builder()
                .title(TITLE)
                .author(author)
                .description(DESCRIPTION)
                .serializedStatus(null)
                .thumbnail(THUMBNAIL)
                .build();

        assertThatThrownBy(() -> webtoonRepository.save(webtoon))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("웹툰 저장시 썸네일 공백 예외 반환.")
    @Test
    void 웹툰_저장시_썸네일_공백_예외_반환(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        Webtoon webtoon = Webtoon.builder()
                .title(TITLE)
                .author(author)
                .description(DESCRIPTION)
                .serializedStatus(SerializedStatus.valueOf(SERIALIZEDSTATUS))
                .thumbnail(null)
                .build();

        assertThatThrownBy(() -> webtoonRepository.save(webtoon))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("웹툰 삭제 정상 케이스.")
    @Test
    void 웹툰_삭제_정상_케이스(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        Webtoon webtoon = Webtoon.builder()
                .title(TITLE)
                .author(author)
                .description(DESCRIPTION)
                .serializedStatus(SerializedStatus.valueOf(SERIALIZEDSTATUS))
                .thumbnail(THUMBNAIL)
                .build();

        Webtoon expectedWebtoon = webtoonRepository.save(webtoon);

        webtoonRepository.delete(expectedWebtoon);

        Optional<Webtoon> result = webtoonRepository.findById(expectedWebtoon.getId());
        assertThat(result.isEmpty());
    }

    @DisplayName("월요일 기준 인기순 웹툰 조회 성공.")
    @Test
    void 월요일_기준_인기순_웹툰_조회_성공(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        PublishingDay monDay = new PublishingDay(DayOfTheWeek.MON);
        publishingDayRepository.save(monDay);

        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        Webtoon webtoon = request.toWebtoon(author);
        webtoonRepository.save(webtoon);

        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);
        memberRepository.save(member);

        InterestedWebtoon i1 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon);
        interestedWebtoonRepository.save(i1);

        WebtoonPublishingDay wpd = WebtoonPublishingDay.updateWebtoonPublishingDay(webtoon, monDay);
        webtoonPublishingDayRepository.save(wpd);

        List<Webtoon> result = webtoonRepository.findOnGoingWebtoonByDayOfTheWeek(DayOfTheWeek.MON);

        assertThat(result).hasSize(2);
        Webtoon retired = result.get(1);
        assertThat(retired.getTitle()).isEqualTo("testTitle");
    }

    @DisplayName("요일별 업데이트순 웹툰 조회 성공.")
    @Test
    void 요일별_업데이트순_웹툰_조회_성공(){
        PublishingDay monDay = new PublishingDay(DayOfTheWeek.MON);
        publishingDayRepository.save(monDay);

        Author author = new Author("테스트");
        authorRepository.save(author);

        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        Webtoon webtoon = request.toWebtoon(author);
        webtoonRepository.save(webtoon);

        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);
        memberRepository.save(member);

        InterestedWebtoon i1 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon);
        interestedWebtoonRepository.save(i1);

        WebtoonPublishingDay wpd = WebtoonPublishingDay.updateWebtoonPublishingDay(webtoon, monDay);
        webtoonPublishingDayRepository.save(wpd);

        List<Webtoon> result = webtoonRepository.findLastUpdatedWebtoonsByDayOfTheWeek(DayOfTheWeek.MON);

        assertThat(result).hasSize(2);
        Webtoon retired = result.get(0);
        assertThat(retired.getTitle()).isEqualTo("testTitle");
    }

    @DisplayName("완결순 인기 웹툰 조회 성공.")
    @Test
    void 완결순_인기_웹툰_조회_성공(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, "COMPLETE",
                List.of("MON"),
                List.of("로맨스")
        );

        WebtoonRegisterRequest request2 = new WebtoonRegisterRequest("TITLE", AUTHOR, DESCRIPTION, THUMBNAIL, "COMPLETE",
                List.of("MON"),
                List.of("로맨스")
        );

        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);
        memberRepository.save(member);

        Webtoon webtoon = request.toWebtoon(author);
        webtoonRepository.save(webtoon);
        Webtoon webtoon2 = request2.toWebtoon(author);
        webtoonRepository.save(webtoon2);

        InterestedWebtoon i1 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon);
        interestedWebtoonRepository.save(i1);
        InterestedWebtoon i2 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon2);
        interestedWebtoonRepository.save(i2);

        List<Webtoon> result = webtoonRepository.findPopularWebtoonsByComplete();

        assertThat(result).hasSize(2);
        Webtoon retired = result.get(0);
        assertThat(retired.getTitle()).isEqualTo("testTitle");
    }

    @DisplayName("완결된 웹툰 업데이트 내림차순 조회 성공.")
    @Test
    void 완결된_웹툰_업데이트_내림차순_조회_성공(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, "COMPLETE",
                List.of("MON"),
                List.of("로맨스")
        );

        WebtoonRegisterRequest request2 = new WebtoonRegisterRequest("TITLE", AUTHOR, DESCRIPTION, THUMBNAIL, "COMPLETE",
                List.of("MON"),
                List.of("로맨스")
        );

        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);
        memberRepository.save(member);

        Webtoon webtoon = request.toWebtoon(author);
        webtoonRepository.save(webtoon);
        Webtoon webtoon2 = request2.toWebtoon(author);
        webtoonRepository.save(webtoon2);

        InterestedWebtoon i1 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon);
        interestedWebtoonRepository.save(i1);
        InterestedWebtoon i2 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon2);
        interestedWebtoonRepository.save(i2);

        List<Webtoon> result = webtoonRepository.findLastestWebtoonsByComplete();

        assertThat(result).hasSize(2);
        Webtoon retired = result.get(0);
        assertThat(retired.getTitle()).isEqualTo("TITLE");
    }

    @DisplayName("해시태그별 웹툰수 조회 성공")
    @Test
    void 해시태그별_웹툰수_조회_성공(){
        Author author = new Author("테스트");
        authorRepository.save(author);

        String hashTag = "로맨스";

        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, "COMPLETE",
                List.of("MON"),
                List.of("로맨스")
        );

        WebtoonRegisterRequest request2 = new WebtoonRegisterRequest("TITLE", AUTHOR, DESCRIPTION, THUMBNAIL, "COMPLETE",
                List.of("MON"),
                List.of("액션")
        );

        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);
        memberRepository.save(member);

        Webtoon webtoon = request.toWebtoon(author);
        webtoonRepository.save(webtoon);
        Webtoon webtoon2 = request2.toWebtoon(author);
        webtoonRepository.save(webtoon2);

        InterestedWebtoon i1 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon);
        interestedWebtoonRepository.save(i1);
        InterestedWebtoon i2 = InterestedWebtoon.updateInterestedWebtoon(member, webtoon2);
        interestedWebtoonRepository.save(i2);

        int resultCount = webtoonRepository.countWebtoonsByHashtag(hashTag);

        assertThat(resultCount).isEqualTo(1);
    }
}
