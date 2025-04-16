package naver.webtoon.project.episode.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.common.time.Timestamped;
import naver.webtoon.project.webtoon.entity.Webtoon;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Episode extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String postscript;

    @Column(nullable = false)
    private Integer views;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "needed_cookie_amount", nullable = false)
    private Integer neededCookieAmount;

    @Column(name = "free_release_date")
    private LocalDate freeReleaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @Builder
    public Episode(Long id, String title, String content, String postscript, Integer views, Boolean isPublic, Integer neededCookieAmount, LocalDate freeReleaseDate, Webtoon webtoon){
        this.id = id;
        this.title = title;
        this.content = content;
        this.postscript = postscript;
        this.views = views;
        this.isPublic = isPublic;
        this.neededCookieAmount = neededCookieAmount;
        this.freeReleaseDate = freeReleaseDate;
        this.webtoon = webtoon;
    }

    public void update(String title, String content, String postscript, Boolean isPublic, LocalDate freeReleaseDate, Integer neededCookieAmount) {
        this.title = title;
        this.content = content;
        this.postscript = postscript;
        this.isPublic = isPublic;
        this.freeReleaseDate = freeReleaseDate;
        this.neededCookieAmount = neededCookieAmount;
    }
}
