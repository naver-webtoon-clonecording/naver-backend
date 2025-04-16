package naver.webtoon.project.episode.controller;

import lombok.RequiredArgsConstructor;
import naver.webtoon.project.common.response.SuccessMessage;
import naver.webtoon.project.episode.dto.request.EpisodeRegisterRequest;
import naver.webtoon.project.episode.dto.request.EpisodeUpdateRequest;
import naver.webtoon.project.episode.service.EpisodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EpisodeController {
    private final EpisodeService episodeService;

    @PostMapping("/webtoon/{webtoonId}")
    public ResponseEntity<SuccessMessage<Void>> registerEpisode(@PathVariable Long webtoonId, @RequestBody EpisodeRegisterRequest request) {
        episodeService.registerWebtoon(webtoonId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드등록성공", null), HttpStatus.CREATED);
    }

    @PutMapping("/episode/{episodeId}")
    public ResponseEntity<SuccessMessage<Void>> updateEpisode(@PathVariable Long episodeId, @RequestBody EpisodeUpdateRequest request) {
        episodeService.updateWebtoon(episodeId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드수정성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/episode/{episodeId}")
    public ResponseEntity<SuccessMessage<Void>> deleteEpisode(@PathVariable Long episodeId) {
        episodeService.deleteEpisode(episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드삭제성공", null), HttpStatus.OK);
    }


}
