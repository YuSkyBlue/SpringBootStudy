package com.example.firstserver.service;

import com.example.firstserver.dto.CommentDto;
import com.example.firstserver.entity.Article;
import com.example.firstserver.entity.Comment;
import com.example.firstserver.repository.ArticleRepository;
import com.example.firstserver.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        //조회 :댓글 목록

        return  commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {

        //게시글 조회 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패 ! 대상 게시물이 없습니다."));
        //댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        //댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);
        //DTO로 변경하여 반혼
        return CommentDto.createCommentDto(created);
    }
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        //댓글 조회 및 예외 발생
        Comment target =commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패 ! 대상 댓글이 없습니다"));

        //댓글 수정
        target.patch(dto);
        //DB로 s
        Comment updated=commentRepository.save(target);
        //댓글 엔티티를 DTo로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }
    @Transactional
    public CommentDto delete(Long id) {
        //댓글 조회 및 예외 발생
        Comment target =commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패 ! 대상 댓글이 없습니다"));
        //댓글 삭제t
        commentRepository.delete(target);
        //삭제 댓글을 DTo로 변환
        return CommentDto.createCommentDto(target);
    }
}
