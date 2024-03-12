package com.kh.demo.domain.comment.dao;

import com.kh.demo.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class CommentDAOImplTest {

  @Autowired
  CommentDAO commentDAO;

  @Test
  @DisplayName("댓글작성테스트")
  void write() {
    Comment comment = new Comment();
    comment.setBnum(1L);
    comment.setContents("댓글");
    comment.setUname("유저1");

    Long userId = commentDAO.write(comment);
  }


  @Test
  void findById() {
    Long bcommentId = 4L;
    Optional<Comment> findedComment = commentDAO.findById(bcommentId);
    Comment comment = findedComment.orElse(null);
    log.info("comment={}", comment);
  }

  @Test
  void deleteById() {
    Long bcommentId = 4L;
    int deletedRowCnt = commentDAO.deleteById(bcommentId);
  }

  @Test
  @DisplayName("댓글수정테스트")
  void updateById() {
    Long bcommentId = 3L;
    Comment comment = new Comment();
    comment.setUname("2L");
    comment.setContents("댓글수정테스트");
    comment.setUname("홍길남3");
    int updatedRowCnt = commentDAO.updateById(bcommentId, comment);
    log.info("updatedRowCnt={}", updatedRowCnt);
  }

  @Test
  @DisplayName("댓글목록페이징테스트")
  void findAll() {
    List<Comment> list = commentDAO.findAll(1L,10L,10L);
    for (Comment comment : list) {
      log.info("comment={}",comment);
    }
    log.info("size={}",list.size());
  }

  @Test
  @DisplayName("댓글여러건등록테스트")
  void testWrite() {
    long start = 1;
    long end = 120;
    for (long i = start; i <= end; i++) {
      Comment comment = new Comment();
      comment.setBnum(1L);
      comment.setContents("댓글"+i);
      comment.setUname("작성자"+i);
      log.info("comment={}",comment);
      Long bcommentId = commentDAO.write(comment);
    }
  }
}