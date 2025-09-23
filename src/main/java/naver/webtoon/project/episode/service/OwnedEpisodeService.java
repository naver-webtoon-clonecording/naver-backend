package naver.webtoon.project.episode.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.common.redis.service.RedisService;
import naver.webtoon.project.episode.dto.response.OwnedEpisodeInfoResponse;
import naver.webtoon.project.episode.dto.response.OwnedEpisodeInfoResponseList;
import naver.webtoon.project.episode.entity.Episode;
import naver.webtoon.project.episode.entity.OwnedEpisode;
import naver.webtoon.project.episode.entity.PaymentType;
import naver.webtoon.project.episode.repository.EpisodeRepository;
import naver.webtoon.project.episode.repository.OwnedEpisodeRepository;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.member.repository.MemberRepository;
import naver.webtoon.project.transaction.entity.CookieTransaction;
import naver.webtoon.project.transaction.repository.CookieTransactionRepository;
import naver.webtoon.project.webtoon.entity.Webtoon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static naver.webtoon.project.common.exception.ErrorCode.*;
import static naver.webtoon.project.episode.entity.PaymentType.FREE;
import static naver.webtoon.project.episode.entity.PaymentType.PAID;

@Service
@RequiredArgsConstructor
public class OwnedEpisodeService {
    private final MemberRepository memberRepository;
    private final EpisodeRepository episodeRepository;
    private final OwnedEpisodeRepository ownedEpisodeRepository;
    private final CookieTransactionRepository cookieTransactionRepository;
    private final RedisService redisService;

    @Transactional
    public void buyEpisode(Member currentMember, Long episodeId) {
        Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_MEMBER));
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));

        if (ownedEpisodeRepository.existsByMemberAndEpisode(member, episode)) {
            throw new WebtoonException(DUPLICATION_OWNED_EPISODE);
        }

        int availableCookie = member.getCookieCount();
        int requiredCookieAmount = episode.getNeededCookieAmount();

        throwIfNotEnoughCookie(availableCookie, requiredCookieAmount);

        member.consumeCookie(requiredCookieAmount);
        OwnedEpisode ownedEpisode = OwnedEpisode.createOwenEpisode(member, episode);
        ownedEpisodeRepository.save(ownedEpisode);

        CookieTransaction cookieTransaction = CookieTransaction.createConsumeCookieTransaction(member, requiredCookieAmount);
        cookieTransactionRepository.save(cookieTransaction);
    }

    private void throwIfNotEnoughCookie(int availableCookie, int requiredCookieAmount) {
        if(availableCookie < requiredCookieAmount){
            throw new WebtoonException(DEFICIENT_COOKIE);
        }
    }

    @Transactional(readOnly = true)
    public OwnedEpisodeInfoResponse readOwnedEpisode(Member currentMember, Long episodeId) {
        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_OWNED_EPISODE));

        Episode episode = ownedEpisode.getEpisode();
        Webtoon webtoon = episode.getWebtoon();

        ownedEpisode.markAsRead();
        episode.incrementView();
        webtoon.incrementTotalViewCount();

        PaymentType paymentType = getEpisodePaymentType(episode.getIsPublic());
        redisService.increaseDailyView(webtoon.getTitle(), paymentType);

        return OwnedEpisodeInfoResponse.toResponse(ownedEpisode);
    }

    private PaymentType getEpisodePaymentType(Boolean isPublic) {
        PaymentType paymentType = PAID;
        if(isPublic) paymentType = FREE;

        return paymentType;
    }

    @Transactional
    public void readOwnEpisodeNextPage(Member currentMember, Long episodeId) {
        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_OWNED_EPISODE));

        Episode episode = ownedEpisode.getEpisode();

        int totalPageCount = episode.getTotalPageCount();
        int currentReadingPage = ownedEpisode.getCurrentReadingPage();

        throwIfInvalidPageNumber(totalPageCount, currentReadingPage);
        ownedEpisode.readNextPage();
    }

    private void throwIfInvalidPageNumber(int totalPageCount, int currentReadingPage) {
        if(totalPageCount <= currentReadingPage){
            throw new WebtoonException(PAGE_OUT_OF_BOUND);
        }
    }

    @Transactional
    public void readOwnEpisodePreviousPage(Member currentMember, Long episodeId) {
        OwnedEpisode ownedEpisode = ownedEpisodeRepository.findByMemberIdAndEpisodeId(currentMember.getId(), episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_OWNED_EPISODE));
        int currentReadingPage = ownedEpisode.getCurrentReadingPage();

        throwIfInvalidPageNumber(currentReadingPage);
        ownedEpisode.readPreviousPage();
    }

    private void throwIfInvalidPageNumber(int currentReadingPage) {
        if(1 >= currentReadingPage){
            throw new WebtoonException(PAGE_OUT_OF_BOUND);
        }
    }

    @Transactional(readOnly = true)
    public OwnedEpisodeInfoResponseList retrieveOwnEpisodesByMember(Member member) {
        List<OwnedEpisode> ownedEpisodes = ownedEpisodeRepository.findByMemberId(member.getId());
        return OwnedEpisodeInfoResponseList.toResponse(ownedEpisodes);
    }
}
