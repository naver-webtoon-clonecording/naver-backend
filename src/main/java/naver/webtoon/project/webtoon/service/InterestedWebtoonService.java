package naver.webtoon.project.webtoon.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.webtoon.dto.response.InterestedWebtoonInfoResponseList;
import naver.webtoon.project.webtoon.entity.InterestedWebtoon;
import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.repository.InterestedWebtoonRepository;
import naver.webtoon.project.webtoon.repository.WebtoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static naver.webtoon.project.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class InterestedWebtoonService {

    private final WebtoonRepository webtoonRepository;
    private final InterestedWebtoonRepository interestedWebtoonRepository;

    @Transactional
    public void registerInterestedWebtoon(Member currentMember, Long webtoonId) {
        throwIfDuflicatedInterestedWebtoon(currentMember.getId(), webtoonId);
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));

        InterestedWebtoon interestedWebtoon = InterestedWebtoon.updateInterestedWebtoon(currentMember, webtoon);
        webtoon.incrementLikeCount();
        interestedWebtoonRepository.save(interestedWebtoon);
    }

    private void throwIfDuflicatedInterestedWebtoon(Long memberId, Long webtoonId) {
        if (interestedWebtoonRepository.existsByMemberIdAndWebtoonId(memberId, webtoonId)) {
            throw new WebtoonException(DUPLICATE_INTERESTED_WEBTOON);
        }
    }

    @Transactional
    public void deleteInterestedWebtoon(Member currentMember, Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));

        InterestedWebtoon interestedWebtoon = interestedWebtoonRepository.findByMemberAndWebtoon(currentMember, webtoon).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_INTERESTED_WEBTOON)
        );

        webtoon.decrementLikeCount();
        interestedWebtoonRepository.delete(interestedWebtoon);
    }

    @Transactional(readOnly = true)
    public InterestedWebtoonInfoResponseList retrieveInterestedWebtoonsByMember(Member member) {
        List<InterestedWebtoon> interestedWebtoons = interestedWebtoonRepository.findByMemberId(member.getId());
        return InterestedWebtoonInfoResponseList.toResponse(interestedWebtoons);
    }
}
