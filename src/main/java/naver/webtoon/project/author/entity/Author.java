package naver.webtoon.project.author.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import naver.webtoon.project.common.time.Timestamped;

@Entity
@Table(name = "author")
@Getter
@NoArgsConstructor
public class Author extends Timestamped {

    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public Author(String name){
        this.name = name;
    }
}
