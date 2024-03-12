package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
  private Long bcommentId;       // 아이디 number(10)
  private Long bnum;             // 원글번호 number(10)
  private String contents;      // 내용 clob
  private String uname;         // 작성자 varchar2(30)
  private LocalDateTime cdate;  // 작성날짜 CDATE TIMESTAMP(6)
  private LocalDateTime udate;  // 수정날짜 UDATE TIMESTAMP(6)
}
