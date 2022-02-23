package com.example.firstserver.dto;

import com.example.firstserver.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {

    private Long id;
    private String title;
    private String content;


    public Article toEntity() {

        return new Article(id, title,content);
    }
}
