package com.example.firstserver.service;

import com.example.firstserver.dto.ArticleForm;
import com.example.firstserver.entity.Article;
import com.example.firstserver.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선 언 ( 서비스 객체를 스프링부트에 생성)
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return  articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
            Article article=dto.toEntity();
            if (article.getId() !=null){
                return null;
            }
            return  articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
         Article article = dto.toEntity();
         log.info("id: {}, article: {}", id, article.toString());

         Article target= articleRepository.findById(id).orElse(null);

         if( target == null || id!= article.getId()){
             log.info("잘못된 요청 ! id{}, article:{}",id, article.toString());
             return null;
         }

         target.patch(article);
         Article updated = articleRepository.save(target);
         return  updated;
    }

    public Article delete(Long id) {
        //대싱 찾기
        Article target = articleRepository.findById(id).orElse(null);

        if(target ==null){
            return  null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        //데이터 반환
        return target;
    }
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변호나
       List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        // entity 묶음을 DB로  저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결재 실패!")
        );

        //결과값 변환
        return  articleList;
    }
}
