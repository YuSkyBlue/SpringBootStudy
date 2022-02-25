package com.example.firstserver.repository;

import com.example.firstserver.entity.Article;
import com.example.firstserver.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA 연동한 테스트
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {//입력 데이터 준비
        Long articleId = 4L;
        //실제 수행
        List<Comment> comments=commentRepository.findByArticleId(articleId);
        //예상하기
        Article article=new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ");
        Comment a = new Comment(1L,article, "Park","굿 윌 헌팅");
        Comment b = new Comment(2L,article, "Kim","아이 엠 샘");
        Comment c = new Comment(3L,article, "Choi","쇼생크 탈출");
        List<Comment> expected = Arrays.asList(a,b,c);
        //검증
        assertEquals(expected.toString(), comments.toString());
        }
        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {//입력 데이터 준비
            Long articleId = 1L;
            //실제 수행
            List<Comment> comments=commentRepository.findByArticleId(articleId);
            //예상하기
            Article article=new Article(1L,"가가가가", "1111");

            List<Comment> expected = Arrays.asList();
            //검증
            assertEquals(expected.toString(), comments.toString(), "1번 글의 글은 댓글을 없음  ");
        }
        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleId = 9L;
            //실제 수행
            List<Comment> comments=commentRepository.findByArticleId(articleId);
            // 예상하기
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(comments.toString(), expected.toString(), "9번 게시글은 존재하지 않는다.");
        }
        /* Case 4: 9999번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleId = 9999L;
            //실제 수행
            List<Comment> comments=commentRepository.findByArticleId(articleId);
            // 예상하기
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(comments.toString(), expected.toString(), "9999번 게시글은 존재하지 않는다.");
        }
        /* Case 5: -1번 게시글의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            Long articleId = -1L;
            //실제 수행
            List<Comment> comments=commentRepository.findByArticleId(articleId);
            // 예상하기
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(comments.toString(), expected.toString(), "9999번 게시글은 존재하지 않는다.");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickName() {
        /* Case 1: "Park"의 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터를 준비
            String nickname = "Park";
            //실제 수행
            List<Comment>comments= commentRepository.findByNickName(nickname);
            //예상하기

            Comment a = new Comment(1L,new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ"), "Park","굿 윌 헌팅");
            Comment b = new Comment(4L,new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Park","치킨");
            Comment c = new Comment(7L,new Article(6L,"당신의 인생 취미는?", "댓글 ㄱㄱㄱ"), "Park","헬스");
            List<Comment> expected = Arrays.asList(a,b,c);
            //검증
            assertEquals(expected.toString(),comments.toString() ,"Park의 모든 댓글을 출력 ");
        }
        /* Case 2: "Kim"의 게시글의 모든 댓글 조회 */
        {
            //입력 데이터를 준비
            String nickname= "Kim";
            //실제 수행
            List<Comment>comments= commentRepository.findByNickName(nickname);
            //예상하기
            Comment a = new Comment(2L,new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ"), "Kim","아이 엠 샘");
            Comment b = new Comment(5L,new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Kim","피자");
            Comment c = new Comment(8L,new Article(6L,"당신의 인생 취미는?", "댓글 ㄱㄱㄱ"), "Kim","조깅");
            List<Comment>expected=Arrays.asList(a,b,c);

            //검증
            assertEquals(expected.toString(),comments.toString());
        }
        /* Case 3: "null"의 게시글의 모든 댓글 조회 */
        {
            //입력 데이터를 준비
            String nickname= "";
            //실제 수행
            List<Comment>comments= commentRepository.findByNickName(nickname);
            //예상하기

            List<Comment>expected=Arrays.asList();

            //검증
            assertEquals(expected.toString(),comments.toString());
        }

        /* Case 4: ""의 게시글의 모든 댓글 조회 */
        {
            //입력 데이터를 준비
            String nickname= "";
            //실제 수행
            List<Comment>comments= commentRepository.findByNickName(nickname);
            //예상하기

            List<Comment>expected=Arrays.asList();

            //검증
            assertEquals(expected.toString(),comments.toString());
        }
    }
    /* Case 5: "i" 들어가 있는 모든 게시글의 모든 댓글 조회 */
    {
        //입력 데이터를 준비
        String nickname= "i";
        //실제 수행
        List<Comment>comments= commentRepository.findByNickName(nickname);
        //예상하기
        Comment a = new Comment(2L,new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ"), "Kim","아이 엠 샘");
        Comment b = new Comment(5L,new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Kim","피자");
        Comment c = new Comment(8L,new Article(6L,"당신의 인생 취미는?", "댓글 ㄱㄱㄱ"), "Kim","조깅");
        Comment d = new Comment(3L,new Article(4L,"당신의 인생 영화는?", "댓글 ㄱ"), "Choi","쇼생크 탈출");
        Comment e = new Comment(6L,new Article(5L,"당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Choi","샌드위치");
        Comment f = new Comment(9L,new Article(6L,"당신의 인생 취미는?", "댓글 ㄱㄱㄱ"), "Choi","유투브");
        List<Comment>expected=Arrays.asList(a,b,c,d,e,f);

        //검증
        assertEquals(expected.toString(),comments.toString());
    }



}