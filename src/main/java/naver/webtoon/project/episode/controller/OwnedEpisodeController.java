package naver.webtoon.project.episode.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.UserDetailsImpl;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.episode.dto.response.OwnedEpisodeInfoResponse;
import naver.webtoon.project.episode.dto.response.OwnedEpisodeInfoResponseList;
import naver.webtoon.project.episode.service.OwnedEpisodeService;
import naver.webtoon.project.transaction.dto.response.CookieTransactionResponseInfoList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/episodes")
public class OwnedEpisodeController {
    private final OwnedEpisodeService ownedEpisodeService;

    @PostMapping("/{episodeId}")
    public ResponseEntity<SuccessMessage<Void>> buyEpisode(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @PathVariable Long episodeId) {
        ownedEpisodeService.buyEpisode(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드 구입 성공", null), HttpStatus.CREATED);
    }

    @GetMapping("/{episodeId}")
    public ResponseEntity<SuccessMessage<OwnedEpisodeInfoResponse>> readOwnedEpisode(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                     @PathVariable Long episodeId) {
        OwnedEpisodeInfoResponse response = ownedEpisodeService.readOwnedEpisode(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("소장 에피소드 조회 성공", response), HttpStatus.OK);
    }

    @PutMapping("/{episodeId}/next")
    public ResponseEntity<SuccessMessage<Void>> readOwnEpisodeNextPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @PathVariable Long episodeId) {
        ownedEpisodeService.readOwnEpisodeNextPage(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드 다음 페이지 읽기 성공", null), HttpStatus.OK);
    }

    @PutMapping("/{episodeId}/previous")
    public ResponseEntity<SuccessMessage<Void>> readOwnEpisodePreviousPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                       @PathVariable Long episodeId) {
        ownedEpisodeService.readOwnEpisodePreviousPage(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드 이전 페이지 읽기 성공", null), HttpStatus.OK);
    }

    @GetMapping("/owned-episode")
    public ResponseEntity<SuccessMessage<OwnedEpisodeInfoResponseList>> retrieveOwnEpisodesByMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        OwnedEpisodeInfoResponseList response = ownedEpisodeService.retrieveOwnEpisodesByMember(userDetails.getMember());
        return new ResponseEntity<>(new SuccessMessage<>("소장 에피소드 리스트 조회 성공", response), HttpStatus.OK);
    }
}
