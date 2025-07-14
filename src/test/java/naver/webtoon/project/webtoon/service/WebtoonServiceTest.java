package naver.webtoon.project.webtoon.service;

import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.author.repository.AuthorRepository;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.dto.request.WebtoonUpdateRequest;
import naver.webtoon.project.webtoon.entity.*;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;
import naver.webtoon.project.webtoon.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static naver.webtoon.project.webtoon.fixture.WebtoonFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WebtoonServiceTest {

    @InjectMocks
    WebtoonService webtoonService;
    @Mock
    WebtoonRepository webtoonRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    PublishingDayRepository publishingDayRepository;
    @Mock
    HashTagRepository hashTagRepository;
    @Mock
    WebtoonPublishingDayRepository webtoonPublishingDayRepository;
    @Mock
    WebtoonHashTagRepository webtoonHashTagRepository;

    @DisplayName("웹툰 생성 시 정상 케이스")
    @Test
    public void registerWebtoon(){
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        Author author = new Author("testAuthorName");
        Webtoon webtoon = request.toWebtoon(author);

       /*
       registerWebtoon() 메서드에 대한 Service 테스트 코드를 작성하려면, 다음과 같은 주요 사항들을 검증해야 합니다:
        authorRepository.findByName()이 정상적으로 작동하는지
        webtoonRepository.save()를 통해 Webtoon이 저장되는지
        saveWebtoonPublishingDay()와 saveWebtoonHashTag()가 호출되는지 (이 메서드들이 외부 의존성에 해당된다면, mocking이 필요)*/

        given(authorRepository.findByName("testAuthorName")).willReturn(Optional.of(author));
        given(webtoonRepository.save(any(Webtoon.class))).willReturn(webtoon);
        given(publishingDayRepository.findByDayOfTheWeek(DayOfTheWeek.MON))
                .willReturn(Optional.of(new PublishingDay(DayOfTheWeek.MON)));
        given(hashTagRepository.findByName("로맨스"))
                .willReturn(Optional.of(new HashTag("로맨스")));

        webtoonService.registerWebtoon(request);

        verify(authorRepository).findByName("testAuthorName");
        verify(webtoonRepository).save(any(Webtoon.class));
        verify(webtoonPublishingDayRepository, times(1)).save(any(WebtoonPublishingDay.class));
        verify(webtoonHashTagRepository, times(1)).save(any(WebtoonHashTag.class));
    }

    @DisplayName("웹툰 생성 시 작가 이름이 없을 경우는 예외를 반환한다.")
    @Test
    void throwsExceptionIfAuthorNotFound() {
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(TITLE, null, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        given(authorRepository.findByName(null)).willReturn(Optional.empty());

        assertThatThrownBy(() -> webtoonService.registerWebtoon(request))
                .isInstanceOf(WebtoonException.class);

    }

    @DisplayName("웹툰 생성시 존재하지 않는 요일이면 예외를 반환한다.")
    @Test
    void throwsExceptionIfPublishingDayNotFound() {
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(
                TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("XYZ"),
                List.of("로맨스")
        );

        assertThatThrownBy(() -> webtoonService.registerWebtoon(request))
                .isInstanceOf(WebtoonException.class);
    }

    @DisplayName("웹툰 생성시 존재하지 않는 해시태그면 예외를 반환한다.")
    @Test
    void throwsExceptionIfHashTagNotFound() {
        WebtoonRegisterRequest request = new WebtoonRegisterRequest(
                TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("WED"),
                List.of("")
        );

        assertThatThrownBy(() -> webtoonService.registerWebtoon(request))
                .isInstanceOf(WebtoonException.class);
    }

    @DisplayName("웹툰 수정 시 정상 케이스")
    @Test
    public void updateWebtoon(){
        Long webtoonId = 1L;
        Author author = new Author("testAuthorName");

        WebtoonRegisterRequest registerRequest = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        Webtoon webtoon = registerRequest.toWebtoon(author);

        ReflectionTestUtils.setField(webtoon, "id", webtoonId);

        WebtoonUpdateRequest request = new WebtoonUpdateRequest(
                "수정된 제목", "수정된 작가명","수정된 설명", "수정된 썸네일", "BREAK",
                List.of("WED"),
                List.of("코믹")
        );

        PublishingDay wed = new PublishingDay(DayOfTheWeek.WED);
        HashTag comicTag = new HashTag("코믹");

        given(webtoonRepository.findById(webtoonId)).willReturn(Optional.of(webtoon));
        given(authorRepository.findByName(anyString())).willReturn(Optional.of(author));
        given(publishingDayRepository.findByDayOfTheWeek(DayOfTheWeek.WED)).willReturn(Optional.of(wed));
        given(hashTagRepository.findByName("코믹")).willReturn(Optional.of(comicTag));

        webtoonService.updateWebtoon(webtoonId, request);

        assertThat(webtoon.getTitle()).isEqualTo(request.getTitle());
        assertThat(webtoon.getDescription()).isEqualTo(request.getDescription());
        assertThat(webtoon.getThumbnail()).isEqualTo(request.getThumbnail());
        assertThat(webtoon.getSerializedStatus().toString()).isEqualTo(request.getSerializedStatus().toString());
        assertThat(webtoon.getAuthor()).isEqualTo(author);

        verify(webtoonPublishingDayRepository).deleteByWebtoonId(webtoonId);
        verify(webtoonHashTagRepository).deleteByWebtoonId(webtoonId);
    }

    @DisplayName("웹툰 수정 시 등록되지 않은 요일 포함되면 예외 발생")
    @Test
    public void throwsExceptionIfPublishingDayNotFoundWhenUpdateWebtoon(){
        Long webtoonId = 1L;
        Author author = new Author("testAuthorName");

        WebtoonRegisterRequest registerRequest = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        Webtoon webtoon = registerRequest.toWebtoon(author);

        ReflectionTestUtils.setField(webtoon, "id", webtoonId);

        WebtoonUpdateRequest request = new WebtoonUpdateRequest(
                "수정된 제목", "수정된 작가명","수정된 설명", "수정된 썸네일", "BREAK",
                List.of("SUN"),
                List.of("코믹")
        );

        given(webtoonRepository.findById(webtoonId)).willReturn(Optional.of(webtoon));
        given(authorRepository.findByName(anyString())).willReturn(Optional.of(author));
        given(publishingDayRepository.findByDayOfTheWeek(DayOfTheWeek.SUN)).willReturn(Optional.empty());

        assertThatThrownBy(() -> webtoonService.updateWebtoon(webtoonId, request))
                .isInstanceOf(WebtoonException.class);
    }

    @DisplayName("웹툰 수정 시 등록되지 않은 해시태그 포함되면 예외 발생")
    @Test
    public void throwsExceptionIfHashTagNotFoundWhenUpdateWebtoon(){
        Long webtoonId = 1L;
        Author author = new Author("testAuthorName");

        WebtoonRegisterRequest registerRequest = new WebtoonRegisterRequest(TITLE, AUTHOR, DESCRIPTION, THUMBNAIL, SERIALIZEDSTATUS,
                List.of("MON"),
                List.of("로맨스")
        );

        Webtoon webtoon = registerRequest.toWebtoon(author);

        ReflectionTestUtils.setField(webtoon, "id", webtoonId);

        WebtoonUpdateRequest request = new WebtoonUpdateRequest(
                "수정된 제목", "수정된 작가명","수정된 설명", "수정된 썸네일", "BREAK",
                List.of("WED"),
                List.of("코믹")
        );

        PublishingDay wed = new PublishingDay(DayOfTheWeek.WED);

        given(webtoonRepository.findById(webtoonId)).willReturn(Optional.of(webtoon));
        given(authorRepository.findByName(anyString())).willReturn(Optional.of(author));
        given(publishingDayRepository.findByDayOfTheWeek(DayOfTheWeek.WED)).willReturn(Optional.of(wed));
        given(hashTagRepository.findByName("코믹")).willReturn(Optional.empty());

        assertThatThrownBy(() -> webtoonService.updateWebtoon(webtoonId, request))
                .isInstanceOf(WebtoonException.class);
    }
}
