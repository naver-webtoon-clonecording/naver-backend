package naver.webtoon.project.webtoon.service;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.exception.WebtoonException;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.webtoon.entity.InterestedWebtoon;
import naver.webtoon.project.webtoon.entity.Webtoon;
import naver.webtoon.project.webtoon.repository.InterestedWebtoonRepository;
import naver.webtoon.project.webtoon.repository.WebtoonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static naver.webtoon.project.common.exception.ErrorCode.DUPLICATE_INTERESTED_WEBTOON;
import static naver.webtoon.project.common.exception.ErrorCode.NOT_FOUND_WEBTOON;

@Service
@RequiredArgsConstructor
public class InterestedWebtoonService {

    private final WebtoonRepository webtoonRepository;
    private final InterestedWebtoonRepository interestedWebtoonRepository;

    @Transactional
    public void registerInterestedWebtoon(Member currentMember, Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));
        throwIfDuflicatedInterestedWebtoon(currentMember, webtoon);

        InterestedWebtoon interestedWebtoon = InterestedWebtoon.updateInterestedWebtoon(currentMember, webtoon);
        interestedWebtoonRepository.save(interestedWebtoon);
    }

    private void throwIfDuflicatedInterestedWebtoon(Member member, Webtoon webtoon) {
        if (interestedWebtoonRepository.existsByMemberAndWebtoon(member, webtoon)) {
            throw new WebtoonException(DUPLICATE_INTERESTED_WEBTOON);
        }
    }
}
