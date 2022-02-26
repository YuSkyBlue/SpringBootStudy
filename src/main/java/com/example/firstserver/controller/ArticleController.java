package com.example.firstserver.controller;

import com.example.firstserver.dto.ArticleForm;
import com.example.firstserver.dto.CommentDto;
import com.example.firstserver.entity.Article;
import com.example.firstserver.repository.ArticleRepository;
import com.example.firstserver.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;


    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //1. Dto 변환 Entity!
        Article article = form.toEntity();
        log.info(article.toString());
        //2. Repository 에게 Entity를 DB에 저장하게 함 !
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/"  +saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id ="+ id);

        //1. 아이디로 데이터를 가져옴
        Article articleEntity =articleRepository.findById(id).orElse(null);
       List<CommentDto> commentDtos =commentService.comments(id);
        //2: 가져온 데이터를 모델에 등록한다
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos",commentDtos);
        //3: 보여줄 페이지를 설정!
        return  "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 아티클을 가져온다
       List<Article> articeEntityList =articleRepository.findAll();

        // 2. 모든 아티클을 묶음을 뷰로 전달
        model.addAttribute("articleList", articeEntityList);
        // 3. 뷰 페잊 ㅣ설정
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public  String edit(@PathVariable Long id, Model model){

        //수정할 데이터 가져오기!
        Article articleEntity =articleRepository.findById(id).orElse(null);
        //모델에 등록해서 view에서 사용하도록
        model.addAttribute("article",articleEntity);
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){

        //1. Dto를 엔티티로 변환한다.
        Article articleEntity =form.toEntity();
        //2: 엔티티를 DB로 저장한다.
        //2-1: DB 에 기존데이터를 가져온다!
        Article target =articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2: 기존 데이터 값을 갱신한다!
        if(target != null){
            articleRepository.save(articleEntity); //Entity 가  DB로 갱신 !
        }
        //3. 수정결과를 페이지로 리다이렉트 한다.
        log.info(form.toString());
        return "redirect:/articles/" + articleEntity.getId();
    }
   @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 드루왔습니다.");
        //1. 삭제 대상을 가져온다
       Article target = articleRepository.findById(id).orElse(null);
       log.info(target.toString());
       //2. 대상을 삭제 한다
       if(target != null){
           articleRepository.delete(target);
           rttr.addFlashAttribute("msg", "삭제가 완료 되었습니다");
       }
       //3. 결과 페이지로 리다이렉트를 한다.
        return  "redirect:/articles";
   }
}


