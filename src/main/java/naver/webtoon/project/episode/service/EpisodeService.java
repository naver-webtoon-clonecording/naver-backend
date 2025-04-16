package naver.webtoon.project.episode.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.episode.dto.request.EpisodeRegisterRequest;
import naver.webtoon.project.episode.dto.request.EpisodeUpdateRequest;
import naver.webtoon.project.episode.entity.Episode;
import naver.webtoon.project.episode.repository.EpisodeRepository;
import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.repository.WebtoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static naver.webtoon.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final WebtoonRepository webtoonRepository;
    private final static int AMOUNT_OF_PUBLIC_EPISODE_COOKIE = 0;

    @Transactional
    public void registerWebtoon(Long webtoonId, EpisodeRegisterRequest request) {
        boolean isPublic = request.getIsPublic();
        int neededCookieAmount = request.getNeededCookieAmount();
        LocalDate freeReleaseDate = request.getFreeReleaseDate();

        throwIfFreeForPrivateEpisode(isPublic, neededCookieAmount);
        throwIfPaidForPublicEpisode(isPublic, neededCookieAmount);

        throwIfFreeReleaseDateEnteredForPublicEpisode(isPublic, freeReleaseDate);
        throwIfFreeReleaseDateIsCurrentDateOrLess(freeReleaseDate);

        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON)
        );
        Episode episode = request.toEpisode(webtoon);
        episodeRepository.save(episode);
    }

    private void throwIfFreeForPrivateEpisode(boolean isPublic, int neededCookieAmount) {
        boolean isFree = (neededCookieAmount == AMOUNT_OF_PUBLIC_EPISODE_COOKIE);
        if(!isPublic && isFree){
            throw new WebtoonException(PRIVATE_EPISODE_MUST_BE_PAID);
        }
    }

    private void throwIfPaidForPublicEpisode(boolean isPublic, int neededCookieAmount) {
        boolean isFree = (neededCookieAmount == AMOUNT_OF_PUBLIC_EPISODE_COOKIE);
        if(isPublic && !isFree){
            throw new WebtoonException(PUBLIC_EPISODE_MUST_BE_FREE);
        }
    }

    private void throwIfFreeReleaseDateEnteredForPublicEpisode(boolean isPublic, LocalDate freeReleaseDate) {
        if(isPublic && freeReleaseDate != null){
            throw new WebtoonException(FREE_EPISODE_MUST_HAVE_FREE_RELEASE_DATE_IS_NULL);
        }
    }

    private void throwIfFreeReleaseDateIsCurrentDateOrLess(LocalDate freeReleaseDate) {
        LocalDate currentDate = LocalDate.now();
        if(freeReleaseDate != null && (currentDate.isAfter(freeReleaseDate) || currentDate.isEqual(freeReleaseDate))){
            throw new WebtoonException(FREE_RELEASE_DATE_MUST_BE_AFTER_THAN_CURRENT_DATE);
        }
    }

    @Transactional
    public void updateWebtoon(Long episodeId, EpisodeUpdateRequest request) {
        String title = request.getTitle();
        String content = request.getContent();
        String postscript = request.getPostscript();
        Boolean isPublic = request.getIsPublic();
        LocalDate freeReleaseDate = request.getFreeReleaseDate();
        Integer neededCookieAmount = request.getNeededCookieAmount();

        throwIfFreeForPrivateEpisode(isPublic, neededCookieAmount);
        throwIfPaidForPublicEpisode(isPublic, neededCookieAmount);
        throwIfFreeReleaseDateEnteredForPublicEpisode(isPublic, freeReleaseDate);
        throwIfFreeReleaseDateIsCurrentDateOrLess(freeReleaseDate);

        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));

        episode.update(title, content, postscript, isPublic, freeReleaseDate, neededCookieAmount);
    }

    public void deleteEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));
        episodeRepository.delete(episode);
    }
}
