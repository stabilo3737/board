package com.kh.demo.domain.board.dao;

import com.kh.demo.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDAO {

  //게시글작성
  Long write(Board board);

  //조회
  Optional<Board> findById(Long userId);

  //단건삭제
  int deleteById(Long userId);

  //여러건삭제
  int deleteByIds(List<Long> userIds);

  //목록
  List<Board> findAll();
  //목록(페이징)
  List<Board> findAll(Long reqPage, Long recCnt);

  //수정
  int updateById(Long userId, Board board);

  //총레코드 건수
  int totalCnt();
}