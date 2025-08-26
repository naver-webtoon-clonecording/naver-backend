package naver.webtoon.project.episode.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.common.time.Timestamped;
import naver.webtoon.project.member.entity.Member;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnedEpisode extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owned_episode_id")
    private Long id;

    @Column(nullable = true)
    private Integer currentReadingPage;

    @Column(nullable = true)
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @Builder
    public OwnedEpisode(Long id, Integer currentReadingPage, Boolean isRead, Member member, Episode episode){
        this.id = id;
        this.currentReadingPage = currentReadingPage;
        this.isRead = isRead;
        this.member = member;
        this.episode = episode;
    }


    public static OwnedEpisode createOwenEpisode(Member member, Episode episode) {
        return OwnedEpisode.builder()
                .currentReadingPage(1)
                .isRead(false)
                .member(member)
                .episode(episode)
                .build();
    }

    public void markAsRead() {
        this.isRead = true;
        this.currentReadingPage = Optional.ofNullable(currentReadingPage)
                .orElse(1);

    }

    public void readNextPage() {
        this.currentReadingPage++;
    }

    public void readPreviousPage() {
        this.currentReadingPage--;
    }
}
