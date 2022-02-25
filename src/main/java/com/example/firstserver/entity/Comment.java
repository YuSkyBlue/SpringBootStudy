package com.example.firstserver.entity;

import com.example.firstserver.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class  Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 여러개 댓글 엔티티가, 하나의 Article에 연관된다.
    @JoinColumn(name = "article_id")
    private  Article article;

    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 처리
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 싪패! 댓글에 id가 없어야한다.");
        if (dto.getArticleId() !=  article.getId()){
            throw  new IllegalArgumentException("댓글 생성 실패 ! 게시글의 id가 잘못되었습니다.");
        }

        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );

    }

    public void patch(CommentDto dto) {
        //예외 발생
        if(this.id !=dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패 ! 잘못된 id가 없습니다");
        }
        //객체를 갱신
        if(dto.getNickname() != null){
            this.nickname= dto.getNickname();
        }
        if(dto.getBody() !=null){
            this.body= dto.getBody();
        }


    }
}
