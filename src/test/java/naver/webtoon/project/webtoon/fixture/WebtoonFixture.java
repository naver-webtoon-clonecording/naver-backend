package naver.webtoon.project.webtoon.fixture;

import naver.webtoon.project.author.entity.Author;
import naver.webtoon.project.member.entity.Member;
import naver.webtoon.project.webtoon.entity.Webtoon;

public class WebtoonFixture {
    public static final String TITLE = "testTitle";
    public static final String AUTHOR = "testAuthorName";
    public static final String THUMBNAIL = "testThumbnail";
    public static final String DESCRIPTION = "testDescription";
    public static final String SERIALIZEDSTATUS = "BREAK";

    public static Webtoon toWebtoon(String title, String thumbnail, String description) {
        return Webtoon.builder()
                .title(title)
                .thumbnail(thumbnail)
                .description(description)
                .build();
    }
}
