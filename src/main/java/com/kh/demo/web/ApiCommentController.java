package com.kh.demo.web;

import com.kh.demo.domain.comment.svc.CommentSVC;
import com.kh.demo.domain.entity.Comment;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.ResCode;
import com.kh.demo.web.req.comment.ReqUpdate;
import com.kh.demo.web.req.comment.ReqWrite;
import com.kh.demo.web.req.comment.ResUpdate;
import com.kh.demo.web.req.comment.ResWrite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ApiCommentController {

  private final CommentSVC commentSVC;

  // 등록
  @PostMapping("/comments")
  public ApiResponse<ResWrite> add(@RequestBody ReqWrite reqWrite) {
    log.info("reqWrite={}", reqWrite);

    Comment comment = new Comment();
    BeanUtils.copyProperties(reqWrite, comment);

    // 게시글 번호 설정
    Long bnum = reqWrite.getBnum();
    comment.setBnum(bnum);

    Long bcommentId = commentSVC.write(comment);

    ResWrite resWrite = new ResWrite(bcommentId, reqWrite.getContents());
    String rtDetail = "댓글번호 " + bcommentId + " 가 등록 되었습니다";

    return ApiResponse.createApiResponseDetail(ResCode.OK.getCode(), ResCode.OK.name(), rtDetail, resWrite);
  }

  // 수정
  @PatchMapping("/comments/{bcommentId}")
  public ApiResponse<?> update(
          @PathVariable("bcommentId") Long bcommentId,
          @RequestBody ReqUpdate reqUpdate
          ){

    Comment comment = new Comment();
    BeanUtils.copyProperties(reqUpdate, comment);

    int updateCount = commentSVC.updateById(bcommentId, comment);
    ApiResponse<ResUpdate> res = null;

    if (updateCount == 1) {
      ResUpdate resUpdate = new ResUpdate();
      BeanUtils.copyProperties(reqUpdate, resUpdate);
      resUpdate.setBcommentId(bcommentId);

      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resUpdate);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }
    return res;
  }

  // 삭제
  @DeleteMapping("/comments/{bcommentId}")
  public ApiResponse<?> delete(@PathVariable("bcommentId") Long bcommentId) {
    int deleteCount = commentSVC.deleteById(bcommentId);
    ApiResponse<ResUpdate> res = null;

    if (deleteCount == 1) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }
    return res;
  }

    // 목록 조회
  @GetMapping("/comments")
  public ApiResponse<?> list(
          @RequestParam("bnum") Long bnum,
          @RequestParam("reqPage") Long reqPage,
          @RequestParam("reqCnt") Long reqCnt){

    try {
      Thread.sleep(1000*1); // 1000이 1초
    }catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    List<Comment> list = commentSVC.findAll(bnum,reqPage,reqCnt);

    ApiResponse<List<Comment>> res = null;
    if(list.size() > 0) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      res.setTotalCnt(commentSVC.totalCnt());
      res.setReqPage(reqPage.intValue());
      res.setRecCnt(reqCnt.intValue());
    }else{
      res = ApiResponse.createApiResponseDetail(
              ResCode.OK.getCode(), ResCode.OK.name(), "등록된 댓글이 1건도 없습니다.", list);
    }
    return res;
  }

}
