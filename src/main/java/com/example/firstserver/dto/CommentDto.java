package com.example.firstserver.dto;

import com.example.firstserver.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

public class CommentDto {
    private  Long id ;
    @JsonProperty("article_id")
    private  Long articleId;
    private  String nickname;
    private  String body;

    public static CommentDto createCommentDto(Comment coment) {
        return new CommentDto(
                coment.getId(),
                coment.getArticle().getId(),
                coment.getNickname(),
                coment.getBody()
        );
    }
}
