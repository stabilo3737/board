package com.kh.demo.domain.board.dao;


import com.kh.demo.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest  // springboot테스트환경 실행
class BoardDAOImplTest {

  @Autowired // springboot 컨테이너의 객체를 주입 받는다.
  BoardDAO boardDAO;


  @Test
  @DisplayName("작성테스트")
  void write() {
    Board board = new Board();
    board.setTitle("제목테스트");
    board.setContents("내용테스트");
    board.setUname("작성자");

    Long userId = boardDAO.write(board);
  }


  @Test
  @DisplayName("조회테스트")
  void findById() {
    Long productId = 1L;
    Optional<Board> findedBoard = boardDAO.findById(productId);
    Board board = findedBoard.orElse(null);
    log.info("board={}", board);
  }


  @Test
  @DisplayName("삭제테스트")
  void deleteById() {
    Long uid = 81L;
    int deletedRowCnt = boardDAO.deleteById(uid);
    Assertions.assertThat(deletedRowCnt).isEqualTo(1);

  }


  @Test
  @DisplayName("수정테스트")
  void updateById() {
    Long userId = 82L;
    Board board = new Board();
    board.setTitle("게시판수정테스트");
    board.setContents("게시판내용수정테스트");
    board.setUname("작성자");
    int updatedRowCnt = boardDAO.updateById(userId, board);
    log.info("updatedRowCnt={}", updatedRowCnt);

  }

  @Test
  @DisplayName("목록조회테스트")
  void findAll() {
    List<Board> list = boardDAO.findAll();
    for (Board board : list) {
      log.info("product={}",board);
    }

  }
}

