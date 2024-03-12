package com.kh.demo.domain.comment.dao;

import com.kh.demo.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
  //댓글작성
  Long write(Comment comment);

  //조회
  Optional<Comment> findById(Long bcommentId);

  //삭제
  int deleteById(Long bcommentId);

  //목록(페이징)
  List<Comment> findAll(Long bnum, Long reqPage, Long reqCnt);

  //수정
  int updateById(Long bcommentId, Comment comment);

  //총레코드 건수
  int totalCnt();
}
