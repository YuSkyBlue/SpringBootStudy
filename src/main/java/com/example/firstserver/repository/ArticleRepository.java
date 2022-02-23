package com.example.firstserver.repository;

import com.example.firstserver.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface ArticleRepository  extends CrudRepository<Article,Long> {
    @Override
    ArrayList<Article> findAll();
}
