package com.example.firstserver.api;

import com.example.firstserver.service.ArticleService;
import com.example.firstserver.dto.ArticleForm;
import com.example.firstserver.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ArticleApiController {

    @Autowired // DI
    private ArticleService articleService;
    //GET
    @GetMapping("/api/articles")
        public List<Article> index(){
         return  articleService.index();

        }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return  articleService.show(id);
    }

    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article>  create(@RequestBody ArticleForm dto){
        Article created= articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH
    @PatchMapping("api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                         @RequestBody ArticleForm dto) {
        // 1. 수정용 엔티티 생성
        Article updated = articleService.update(id, dto);
        return (updated != null)?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
   @DeleteMapping("/api/articles/{id}")
    public  ResponseEntity<Article> delete(@PathVariable Long id){

       Article deleted =articleService.delete(id);


        return (deleted != null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

   }
    // 트랜잭션 -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> tranactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList=articleService.createArticles(dtos);
        return  (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
