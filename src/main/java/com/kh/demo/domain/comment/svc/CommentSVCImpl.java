package com.kh.demo.domain.comment.svc;

import com.kh.demo.domain.comment.dao.CommentDAO;
import com.kh.demo.domain.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentSVCImpl implements CommentSVC{

  private CommentDAO commentDAO;

  public CommentSVCImpl(CommentDAO commentDAO) {
    this.commentDAO = commentDAO;
  }

  @Override
  public Long write(Comment comment) {
    return commentDAO.write(comment);
  }

  @Override
  public Optional<Comment> findById(Long bcommentId) {
    return commentDAO.findById(bcommentId);
  }

  @Override
  public int deleteById(Long bcommentId) {
    return commentDAO.deleteById(bcommentId);
  }

  @Override
  public int updateById(Long bcommentId, Comment comment) {
    return commentDAO.updateById(bcommentId,comment);
  }


  @Override
  public List<Comment> findAll(Long bnum, Long reqPage, Long reqCnt) {
    return commentDAO.findAll(bnum, reqPage, reqCnt);
  }

  @Override
  public int totalCnt() {
    return commentDAO.totalCnt();
  }

}
