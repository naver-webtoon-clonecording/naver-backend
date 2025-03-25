package naver.webtoon.project.webtoon.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.author.repository.AuthorRepository;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.webtoon.dto.request.WebtoonUpdateRequest;
import naver.webtoon.project.webtoon.dto.response.WebtoonInfoListResponse;
import naver.webtoon.project.webtoon.entity.*;
import naver.webtoon.project.webtoon.dto.request.WebtoonRegisterRequest;
import naver.webtoon.project.webtoon.entity.enums.DayOfTheWeek;
import naver.webtoon.project.webtoon.entity.enums.SerializedStatus;
import naver.webtoon.project.webtoon.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static naver.webtoon.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;
    private final AuthorRepository authorRepository;
    private final PublishingDayRepository publishingDayRepository;
    private final WebtoonPublishingDayRepository webtoonPublishingDayRepository;
    private final HashTagRepository hashTagRepository;
    private final WebtoonHashTagRepository webtoonHashTagRepository;

    @Transactional
    public void registerWebtoon(WebtoonRegisterRequest request){
        Author author = authorRepository.findByName(request.getAuthor()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_AUTHOR));
        Webtoon webtoon = request.toWebtoon(author);

        webtoonRepository.save(webtoon);
        saveWebtoonPublishingDay(webtoon, request);
        saveWebtoonHashTag(webtoon, request);
    }

    private void saveWebtoonPublishingDay(Webtoon webtoon, WebtoonRegisterRequest request) {
        for (String dayOfTheWeek : request.getPublishingDay()) {
            DayOfTheWeek dayOfTheWeekEnum = DayOfTheWeek.toEnum(dayOfTheWeek);
            PublishingDay publishingDay = publishingDayRepository.findByDayOfTheWeek(dayOfTheWeekEnum).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_PUBLISHING_DAY));

            WebtoonPublishingDay webtoonPublishingDay = request.toWebtoonPublishingDay(webtoon, publishingDay);
            webtoonPublishingDayRepository.save(webtoonPublishingDay);
        }
    }

    private void saveWebtoonHashTag(Webtoon webtoon, WebtoonRegisterRequest request) {
        for (String name : request.getHashTag()) {
            HashTag hashTag = hashTagRepository.findByName(name).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_HASH_TAG));

            WebtoonHashTag webtoonHashTag = request.toWebtoonHashTag(webtoon, hashTag);
            webtoonHashTagRepository.save(webtoonHashTag);
        }
    }

    @Transactional
    public void updateWebtoon(Long webtoonId, WebtoonUpdateRequest request) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));
        updateWebtoon(webtoon, request);
        updateWebtoonPublishingDay(webtoon, request);
        updateWebtoonHashTag(webtoon, request);
    }

    private void updateWebtoon(Webtoon webtoon, WebtoonUpdateRequest request){
        String title = request.getTitle();
        String description = request.getDescription();
        String thumbnail = request.getThumbnail();
        SerializedStatus serializedStatus = SerializedStatus.toEnum(request.getSerializedStatus());
        Author author = authorRepository.findByName(request.getAuthor()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_AUTHOR));

        webtoon.update(title, description, thumbnail, serializedStatus, author);
    }

    private void updateWebtoonPublishingDay(Webtoon webtoon, WebtoonUpdateRequest request) {
        webtoonPublishingDayRepository.deleteByWebtoonId(webtoon.getId());
        saveWebtoonPublishingDay(webtoon, request);
    }

    private void saveWebtoonPublishingDay(Webtoon webtoon, WebtoonUpdateRequest request) {
        for (String dayOfTheWeek : request.getPublishingDay()) {
            DayOfTheWeek dayOfTheWeekEnum = DayOfTheWeek.toEnum(dayOfTheWeek);
            PublishingDay publishingDay = publishingDayRepository.findByDayOfTheWeek(dayOfTheWeekEnum).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_PUBLISHING_DAY));
            WebtoonPublishingDay webtoonPublishingDay = request.toWebtoonPublishingDay(webtoon, publishingDay);

            webtoonPublishingDayRepository.save(webtoonPublishingDay);
        }
    }

    private void updateWebtoonHashTag(Webtoon webtoon, WebtoonUpdateRequest request) {
        webtoonHashTagRepository.deleteByWebtoonId(webtoon.getId());
        saveWebtoonHashTag(webtoon, request);
    }

    private void saveWebtoonHashTag(Webtoon webtoon, WebtoonUpdateRequest request) {
        for (String name : request.getHashTag()) {
            HashTag hashTag = hashTagRepository.findByName(name).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_HASH_TAG));

            WebtoonHashTag webtoonHashTag = request.toWebtoonHashTag(webtoon, hashTag);
            webtoonHashTagRepository.save(webtoonHashTag);
        }
    }

    @Transactional
    public void deleteWebtoon(Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));
        webtoonHashTagRepository.deleteByWebtoonId(webtoon.getId());
        webtoonPublishingDayRepository.deleteByWebtoonId(webtoon.getId());
        webtoonRepository.delete(webtoon);
    }

    public WebtoonInfoListResponse getPopularWebtoonsByDayOfWeekAndWithin30Days(String publishingDay) {
        DayOfTheWeek dayOfTheWeek = DayOfTheWeek.toEnum(publishingDay);
        List<Webtoon> webtoons = webtoonRepository.findOnGoingWebtoonByDayOfTheWeek(dayOfTheWeek);
        return WebtoonInfoListResponse.toResponse(webtoons);
    }

    public WebtoonInfoListResponse getPopularWebtoonsByComplete() {
        List<Webtoon> webtoons = webtoonRepository.findPopularWebtoonsByComplete();
        return WebtoonInfoListResponse.toResponse(webtoons);
    }
}
