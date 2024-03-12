package com.kh.demo.web;

import com.kh.demo.domain.board.svc.BoardSVC;
import com.kh.demo.domain.entity.Board;
import com.kh.demo.web.form.board.AddForm;
import com.kh.demo.web.form.board.UpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller // Controller 역할을 하는 클래스
@RequestMapping("/boards")    // http://localhost:9080/boards
public class BoardController {

  private BoardSVC boardSVC;

  BoardController(BoardSVC boardSVC) {
    this.boardSVC = boardSVC;
  }

  //게시글등록양식
  @GetMapping("/add")         // Get, http://localhost:9080/boards/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "board/add";     // view이름  게시글등록화면
  }

  //게시글등록처리
  @PostMapping("/add")        // Post, http://localhost:9080/boards/add
  public String add(
          AddForm addForm,
          Model model,
          RedirectAttributes redirectAttributes
          ){
    //유효성체크
    //필드 레벨
    //1-1)제목
    String pattern = "^[a-zA-Z0-9가-힣_-]{2,10}$";
    if (!Pattern.matches(pattern, addForm.getTitle())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_title","영문/숫자/한글/_-가능, 2~10자리");
      return "board/add";
    }
    //1-2)내용
    pattern = "^[a-zA-Z0-9가-힣_-]{2,500}$";
    if (!Pattern.matches(pattern, addForm.getContents())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_contents","영문/숫자/한글/_-가능, 2~500자리");
      return "board/add";
    }
    //1-3)작성자
    pattern = "^[a-zA-Z0-9가-힣_-]{2,10}$";
    if (!Pattern.matches(pattern, addForm.getUname())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_uname","영문/숫자/한글/_-가능, 2~10자리");
      return "board/add";
    }

    //게시글등록
    Board board = new Board();
    board.setTitle(addForm.getTitle());
    board.setContents(addForm.getContents());
    board.setUname(addForm.getUname());
    board.setEmail(addForm.getEmail());

    Long userId = boardSVC.write(board);

    redirectAttributes.addAttribute("uid",userId);
    return "redirect:/boards/{uid}/detail"; // 게시글조회화면  302 GET http://서버:9080/boards/유저아이디/detail

  }
  //조회
  @GetMapping("/{uid}/detail")       //GET http://localhost:9080/boards/유저아이디/detail
  public String findById(@PathVariable("uid") Long userId, Model model){

    Optional<Board> findedBoard = boardSVC.findById(userId);
    Board board = findedBoard.orElseThrow();
    model.addAttribute("board", board);

    return "board/detailForm";
  }

  //단건삭제
  @GetMapping("/{uid}/del")
  public String deleteById(@PathVariable("uid") Long userId){

    //1)상품 삭제 -> 상품테이블에서 삭제
    int deletedRowCnt = boardSVC.deleteById(userId);

    return "redirect:/boards";     // GET http://localhost:9080/boards/
  }

  //여러건삭제
  @PostMapping("/del")          // POST http://localhost:9080/boards/del
  public String deleteByIds(@RequestParam("uids") List<Long> uids) {

    int deletedRowCnt = boardSVC.deleteByIds(uids);

    return "redirect:/boards";    // GET http://localhost:9080/boards/
  }

  //수정양식
  @GetMapping("/{uid}/edit")      // GET http://locahost:9080/boards/아이디/edit
  public String updateForm(
          @PathVariable("uid") Long userId,
          Model model){

    Optional<Board> optionalBoard = boardSVC.findById(userId);
    Board findedBoard = optionalBoard.orElseThrow();

    model.addAttribute("board",findedBoard);
    return "board/updateForm";
  }

  //수정 처리
  @PostMapping("/{uid}/edit")
  public String update(
          //경로변수 uid로부터 상품번호을 읽어온다
          @PathVariable("uid") Long userId,
          //요청메세지 바디로부터 대응되는 상품정보를 읽어온다.
          UpdateForm updateForm,
          //리다이렉트시 경로변수에 값을 설정하기위해 사용
          RedirectAttributes redirectAttributes,
          Model model){

    //유효성체크
    //필드 레벨
    //1-1)제목
    String pattern = "^[a-zA-Z0-9가-힣_-]{2,10}$";
    if (!Pattern.matches(pattern, updateForm.getTitle())) {
      model.addAttribute("board", updateForm);
      model.addAttribute("s_err_title","영문/숫자/한글/_-가능, 2~10자리");
      return "board/updateForm";
    }
    //1-2)내용
    pattern = "^[a-zA-Z0-9가-힣_-]{2,500}$";
    if (!Pattern.matches(pattern, updateForm.getContents())) {
      model.addAttribute("board", updateForm);
      model.addAttribute("s_err_contents","영문/숫자/한글/_-가능, 2~500자리");
      return "board/updateForm";
    }
    //1-3)작성자
    pattern = "^[a-zA-Z0-9가-힣_-]{2,10}$";
    if (!Pattern.matches(pattern, updateForm.getUname())) {
      model.addAttribute("board", updateForm);
      model.addAttribute("s_err_uname","영문/숫자/한글/_-가능, 2~10자리");
      return "board/updateForm";
    }

    //정상처리
    Board board = new Board();
    board.setTitle(updateForm.getTitle());
    board.setUname(updateForm.getUname());
    board.setContents(updateForm.getContents());
    int updateRowCnt = boardSVC.updateById(userId, board);

    redirectAttributes.addAttribute("uid",userId);
    return "redirect:/boards/{uid}/detail";
  }

//  //목록
//  @GetMapping   // GET http://localhost:9080/boards
//  public String findAll(Model model){
//
//    List<Board> list = boardSVC.findAll();
//    model.addAttribute("list", list);
//
//    return "board/all";
//  }

  //목록(페이징)
  @GetMapping   // GET http://localhost:9080/boards?reqPage=2&reqCnt=10
  public String findAllByPaging(
          Model model,
          @RequestParam(value = "reqPage", defaultValue = "1") Long reqPage, // 요청 페이지
          @RequestParam(value = "reqCnt", defaultValue = "10") Long reqCnt,   // 레코드 수
          @RequestParam(value = "cpgs", defaultValue = "1") Long cpgs,   //페이지 그룹 시작번호
          @RequestParam(value = "cp", defaultValue = "1") Long cp   // 현재 페이지
  ){

    List<Board> list = boardSVC.findAll(reqPage, reqCnt);
    int totalCnt = boardSVC.totalCnt();

    model.addAttribute("list", list);
    model.addAttribute("totalCnt", totalCnt);
    model.addAttribute("cpgs", cpgs);
    model.addAttribute("cp", cp);

    return "board/allByPaging";
  }
}